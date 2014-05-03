package com.roloduck.web.controller.partner;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.service.PartnerService;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.models.projpartassoc.service.ProjPartAssocService;
import com.roloduck.user.User;
import com.roloduck.utils.JSONUtils;
import com.roloduck.utils.SecurityUtils;
import com.roloduck.web.exception.ProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/21/14
 * RoloDuck
 */

@Controller
@RequestMapping("/partners")
@SessionAttributes(value = {"companyName", "projects"})
public class PartnerController extends ProcessException {

    @Autowired
    private ProjPartAssocService associationService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping
    public String servePartners(ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            // Add the current company, and the list of company's partners to the model
            model.addAttribute("companyName", companyService.restoreCompanyById(user.getCompanyId()).getCompanyName());
            // Find all the partners
            List<Partner> partners = partnerService.findAllCompanyPartnersSortedAlphabetically(user.getCompanyId());
            // For each partner, find its associated projects
            for(Partner p: partners) {
                p.setAssociatedProjects(partnerService.findAllConnectedProjects(p.getId()));
                p.setAssociatedContacts(partnerService.findAllAssociatedContacts(p.getId()));
            }
            model.addAttribute("partners", partners);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "partners";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String servePartnerCreate(ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            Company company = companyService.restoreCompanyById(user.getCompanyId());
            model.addAttribute("companyName", company.getCompanyName());
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "partners-create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String postPartnerCreate(@ModelAttribute("partner") Partner partner, ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            partnerService.createPartner(partner, user);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            // Return back to the create page with the error message
            return "partners-create";
        }
        return "redirect:/partners";
    }

    @RequestMapping(value = "/{partnerId}", method = RequestMethod.GET)
    public String servePartnerSingle(@PathVariable long partnerId, ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            Company company = companyService.restoreCompanyById(user.getCompanyId());
            model.addAttribute("companyName", company.getCompanyName());
            Partner partner = partnerService.restoreById(partnerId);
            partner.setAssociatedProjects(partnerService.findAllConnectedProjects(partnerId));
            partner.setAssociatedContacts(partnerService.findAllAssociatedContacts(partnerId));
            model.addAttribute("partner", partner);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "partners-single";
    }

    /**
     * Serve the assignment page, where a user can assign various projects to a specific partner
     * The partner is denoted by the id
     * @param partnerId the id of the partner receiving assignments
     * @param model the model
     * @return the partner assign tiles definition
     */
    @RequestMapping(value = "/assign={partnerId}", method = RequestMethod.GET)
    public String servePartnerAssignProject(@PathVariable long partnerId, ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            Company company = companyService.restoreCompanyById(user.getCompanyId());
            model.addAttribute("companyName", company.getCompanyName());
            Partner partner = partnerService.restoreById(partnerId);
            model.addAttribute("partner", partner);
            List<Project> projects = projectService.findAllCompanyProjectsSortedAlphabetically(company.getId());
            model.addAttribute("projects", projects);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "partners-assign";
    }

    /**
     * Post the assignment of a project to the given partner
     * @param partnerId the id of the partner receiving the assignment
     * @param project the project being assigned to the partner
     * @param model the model
     * @return if successful, return the user to the partner page, else return back to the assignment page
     */
    @RequestMapping(value = "/assign={partnerId}", method = RequestMethod.POST)
    public String postPartnerAssignProject(@PathVariable long partnerId, @ModelAttribute("project") Project project, ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException sle) {
            processRDException(model,    sle);
        }
        try {
            Partner partner = partnerService.restoreById(partnerId);
            model.addAttribute("partner", partner);
            if(project.getId() > 0) {
                associationService.assignPartnerToProject(partner.getId(), project.getId());
            } else {
                if(project.getProjectName() != null && !project.getProjectName().equalsIgnoreCase("")) {
                    project.setProjectName(project.getProjectName());
                    project.setProjectDescription(project.getProjectDescription());
                    projectService.createProject(project, user);
                    associationService.assignPartnerToProject(partner.getId(), project.getId());
                }
            }
        } catch (ServiceLogicException sle) {
            processRDException(model, sle);
            return "partners-assign";
        }
        return "redirect:/partners/{partnerId}";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void postRemovePartner(HttpServletRequest request, HttpServletResponseWrapper response, ModelMap model) {
        String deletedPartner = request.getParameter("deleted");
        long partnerId = Long.valueOf(deletedPartner);
        try {
            partnerService.removePartner(partnerId);
            model.addAttribute("isValid", true);
        } catch (ServiceLogicException sle) {
            model.addAttribute("isValid", false);
            // TODO write the exception back to the javascript
            processRDException(model, sle);
        }
        try {
            JSONUtils.write(response, model);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @RequestMapping(value = "/unassign", method = RequestMethod.POST)
    public void postRemoveAssociation(HttpServletRequest request, HttpServletResponseWrapper response,
                                      ModelMap model) {
        System.out.println("Hello!");
        String projectIdStr = request.getParameter("projectId");
        String partnerIdStr = request.getParameter("partnerId");
        long projectId = Long.valueOf(projectIdStr);
        long partnerId = Long.valueOf(partnerIdStr);
        try {
            associationService.unassignPartnerFromProject(partnerId, projectId);
            model.addAttribute("isValid", true);
        } catch (ServiceLogicException sle) {
            model.addAttribute("isValid", false);
            // TODO write the exception back to the javascript
            processRDException(model, sle);
        }
        try {
            JSONUtils.write(response, model);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
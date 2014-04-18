package com.roloduck.web.controller.partner;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.service.PartnerService;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.user.User;
import com.roloduck.utils.SecurityUtils;
import com.roloduck.web.exception.ProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/21/14
 * RoloDuck
 */

@Controller
@RequestMapping("/partners")
@SessionAttributes("companyName")
public class PartnerController extends ProcessException {

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
            List<Partner> partners = partnerService.findAllCompanyPartners(user.getCompanyId());
            // For each partner, find its associated projects
            for(Partner p: partners) {
                p.setAssociatedProjects(partnerService.findAllConnectedProjects(p.getId()));
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
            model.addAttribute("partner", partner);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "partners-single";
    }

    @RequestMapping(value = "/assign={partnerId}", method = RequestMethod.GET)
    public String servePartnerAssignProject(@PathVariable long partnerId, ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            Company company = companyService.restoreCompanyById(user.getCompanyId());
            model.addAttribute("companyName", company.getCompanyName());
            Partner partner = partnerService.restoreById(partnerId);
            model.addAttribute("partner", partner);
            List<Project> projects = projectService.findAllCompanyProjects(company.getId());
            model.addAttribute("projects", projects);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "partners-assign";
    }

    @RequestMapping(value = "/assign={partnerId}", method = RequestMethod.POST)
    public String postPartnerAssignProject(@PathVariable long partnerId, @ModelAttribute("project") Project project, ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        try {
            Partner partner = partnerService.restoreById(partnerId);
            model.addAttribute("partner", partner);
            if(project.getId() > 0) {
                partnerService.assignPartnerToProject(partner, project.getId());
            } else {
                if(project.getProjectName() != null && !project.getProjectName().equalsIgnoreCase("")) {
                    project.setProjectName(project.getProjectName());
                    project.setProjectDescription(project.getProjectDescription());
                    projectService.createProject(project, user);
                    partnerService.assignPartnerToProject(partner, project.getId());
                }
            }
        } catch (ServiceLogicException sle) {
            processRDException(model, sle);
            return "partners-assign";
        }
        return "partners-single";
    }
}
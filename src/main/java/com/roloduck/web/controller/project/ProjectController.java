package com.roloduck.web.controller.project;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

@Controller
@SessionAttributes("companyName")
public class ProjectController extends ProcessException {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProjPartAssocService projPartAssocService;
    @Autowired
    private PartnerService partnerService;

    private static final String URI_PREFIX = "/projects";

    @RequestMapping(value = URI_PREFIX)
    public String serveProjects(ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
            // Add the current user, his company, and the list of his company's projects to the model
            model.addAttribute("user", user);
            model.addAttribute("companyName", companyService.restoreCompanyById(user.getCompanyId()).getCompanyName());
            List<Project> projects = projectService.findAllCompanyProjectsSortedAlphabetically(user.getCompanyId());

            // This map will store a count of contacts for each project the user has
            Map<Long, Integer> contactsCountMap = new HashMap<>();
            for(Project proj: projects) {
                Integer contactsCount = 0;
                List<Partner> partners = projPartAssocService.findAssociatedPartnersToProject(proj.getId());
                // For each partner relate to the current project, find all contacts
                for(Partner p: partners) {
                    p.setAssociatedProjects(partnerService.findAllConnectedProjects(p.getId()));
                    p.setAssociatedContacts(partnerService.findAllAssociatedContacts(p.getId()));
                    contactsCount += p.getAssociatedContacts().size();
                }
                contactsCountMap.put(proj.getId(), contactsCount);
                proj.setPartnerAssocs(partners);
            }
            model.addAttribute("projects", projects);
            model.addAttribute("contactsMap", contactsCountMap);
        } catch(ServiceLogicException e) {
            processRDException(model, e);
        }
        return "projects";
    }

    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.GET)
    public String serveProjectsCreate(ModelMap model) {
        User user = null;
        Company company = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException e) {
            processRDException(model, e);
            return "redirect:/";
        }
        try {
            company = companyService.restoreCompanyByUser(user);
            model.addAttribute("companyName", company.getCompanyName());
            return "projects-create";
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "redirect:/";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.POST)
    public String postProjectsCreate(@ModelAttribute("project") Project project, ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
            projectService.createProject(project, user);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "projects-create";
        }
        return "redirect:/projects";
    }

    @RequestMapping(value = URI_PREFIX + "/{projectId}", method = RequestMethod.GET)
    public String serveProjectSingle(@PathVariable long projectId, ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            Company company = companyService.restoreCompanyById(user.getCompanyId());
            model.addAttribute("companyName", company.getCompanyName());
            Project project = projectService.restoreProjectById(projectId);
            model.addAttribute("project", project);
            List<Partner> partners = projPartAssocService.findAssociatedPartnersToProject(project.getId());
            for(Partner p: partners) {
                p.setAssociatedProjects(partnerService.findAllConnectedProjects(p.getId()));
                p.setAssociatedContacts(partnerService.findAllAssociatedContacts(p.getId()));
            }
            model.addAttribute("partners", partners);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "project-single";
    }

    @RequestMapping(value = "/removeAssociation", method = RequestMethod.POST)
    public String postRemoveProject(ModelMap model, HttpServletRequest request, HttpServletResponseWrapper response) {
        String deletedProject = request.getParameter("deleted");
        long projectId = Long.valueOf(deletedProject);
        try {
            projectService.removeProject(projectId);
            model.addAttribute("isValid", true);
        } catch (ServiceLogicException sle) {
            model.addAttribute("isValid", false);
            // TODO write the exception back to the javascript
            processRDException(model, sle);
        }
        try {
            JSONUtils.write(response, model);
            return "true";
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return "false";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/edit={projectId}", method = RequestMethod.GET)
    public String serveProjectsEdit(@PathVariable long projectId, ModelMap model) {
        User user = null;
        Company company = null;

        try {
            user = SecurityUtils.getCurrentUser();
            company = companyService.restoreCompanyByUser(user);
            model.addAttribute("companyName", company.getCompanyName());
            Project project = projectService.restoreProjectById(projectId);
            model.addAttribute("project", project);
            return "projects-create";
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "redirect:/";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/edit", method = RequestMethod.POST)
    public String postProjectEdit(@ModelAttribute("project") Project project, ModelMap model) {
        try {
            SecurityUtils.getCurrentUser();
            projectService.updateProject(project);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            // Return back to the create page with the error message
            return "projects-create";
        }
        return "redirect:/projects";
    }
}

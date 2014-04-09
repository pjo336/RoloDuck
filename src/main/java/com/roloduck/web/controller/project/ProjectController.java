package com.roloduck.web.controller.project;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.user.User;
import com.roloduck.utils.SecurityUtils;
import com.roloduck.web.exception.ProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;

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

    private static final String URI_PREFIX = "/projects";

    @RequestMapping(value = URI_PREFIX)
    public String serveProjects(ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
            // Add the current user, his company, and the list of his company's projects to the model
            model.addAttribute("user", user);
            model.addAttribute("companyName", companyService.restoreCompanyById(user.getCompanyId()).getCompanyName());
            model.addAttribute("projects", projectService.findAllCompanyProjects(user.getCompanyId()));
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

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public String servePartnerSingle(@PathVariable long projectId, ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            Company company = companyService.restoreCompanyById(user.getCompanyId());
            model.addAttribute("companyName", company.getCompanyName());
            Project project = projectService.restoreProjectById(projectId);
            model.addAttribute("project", project);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "project-single";
    }

    @RequestMapping(value = "/deleteProjects")
    public void deleteprojects(ModelMap model, HttpServletRequest request, HttpServletResponseWrapper result) {
        String[] deletedProjectIds = request.getParameter("deleted").split(",");
        for(String s: deletedProjectIds) {
            long projectId = Long.valueOf(s);
            try {
                Project project = projectService.restoreProjectById(projectId);
            } catch (ServiceLogicException sle) {
                processRDException(model, sle);
            }
        }
    }
}

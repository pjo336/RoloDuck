package com.roloduck.web.controller.project;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.user.User;
import com.roloduck.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

@Controller
public class ProjectController {

    static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

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
            logger.error("There was a problem trying to serve Projects");
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
            // TODO propogate this to the front end?
            logger.warn("An anonymous user attempted to access /projects/create.");
            return "redirect:/";
        }
        try {
            company = companyService.restoreCompanyByUser(user);
            model.addAttribute("companyName", company.getCompanyName());
            return "projects-create";
        } catch(ServiceLogicException ble) {
            logger.error("The user: " + user.getEmail() + " does not belong to a company. Rerouting to index.");
            return "redirect:/";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.POST)
    public String postProjectsCreate(@ModelAttribute("project") Project project, ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException e) {
            logger.error("An anonymous user attempted to create a project.");
        }
        try {
            projectService.createProject(project, user);
        } catch(ServiceLogicException e) {
            logger.error("There was a problem creating the project named: " + project.getProjectName());
        }
        return "redirect:/projects";
    }

    @RequestMapping(value = "/deleteProjects")
    public void deleteprojects(ModelMap model, HttpServletRequest request, HttpServletResponseWrapper result) {
        String[] deletedProjectIds = request.getParameter("deleted").split(",");
        for(String s: deletedProjectIds) {
            long projectId = Long.valueOf(s);
            try {
                Project project = projectService.restoreProjectById(projectId);
                System.out.println(project.getProjectName());
            } catch (ServiceLogicException sle) {
                sle.printStackTrace();
            }
        }
    }
}

package com.roloduck.web.project;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.model.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.project.model.Project;
import com.roloduck.user.model.User;
import com.roloduck.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


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
    private CompanyService companyService;

    private static final String URI_PREFIX = "/projects";

    @RequestMapping(value=URI_PREFIX)
    public String serveProjects(ModelMap model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("page", "projects");
        return "projects";
    }

    @RequestMapping(value=URI_PREFIX + "/create", method = RequestMethod.GET)
    public String serveProjectsCreate(ModelMap model) {
        model.addAttribute("page", "projects");
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
            logger.warn("The user: " + user.getEmail() + " does not belong to a company. Rerouting to index.");
            return "redirect:/";
        }
    }

    @RequestMapping(value=URI_PREFIX + "/create", method = RequestMethod.POST)
    public String postProjectsCreate(@ModelAttribute("project") Project project, ModelMap model, Principal principal) {
        System.out.println(project.getProjectName());
        return "redirect:/projects";
    }
}

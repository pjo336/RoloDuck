package com.roloduck.web.index;

import com.roloduck.exception.NotFoundException;
import com.roloduck.models.company.dao.CompanyDAO;
import com.roloduck.models.company.model.Company;
import com.roloduck.models.project.model.Project;
import com.roloduck.user.model.User;
import com.roloduck.user.service.UserService;
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
 * @since 3/8/14
 * RoloDuck
 */

@Controller
public class IndexController {

    @Autowired
    CompanyDAO companyDAO;

    @Autowired
    UserService userService;

    @RequestMapping(value="/")
    public String serveIndex(ModelMap model, Principal principal) {
        model.addAttribute("page", "projects");
        return "index";
    }

    @RequestMapping(value="/projects")
    public String serveProjects(ModelMap model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("page", "projects");
        return "projects";
    }

    @RequestMapping(value="/projects/create", method = RequestMethod.GET)
    public String serveProjectsCreate(ModelMap model, Principal principal) throws NotFoundException {
        model.addAttribute("page", "projects");
        User user = userService.restoreUserById(1);
        Company company = companyDAO.restoreById(user.getCompanyId());
        model.addAttribute("companyName", company.getCompanyName());
        return "projects-create";
    }

    @RequestMapping(value="/projects/create", method = RequestMethod.POST)
    public String postProjectsCreate(@ModelAttribute("project") Project project, ModelMap model, Principal principal) {
        System.out.println(project.getProjectName());
        return "redirect:/projects";
    }
}

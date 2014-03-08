package com.roloduck.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/8/14
 * RoloDuck
 */

@Controller
public class IndexController {

    @RequestMapping(value="/")
    public String serveIndex(ModelMap model, Principal principal) {
        if(principal != null) {
            // TODO checking if the user is logged in should be done in java in JSPs
            model.addAttribute("principal", principal);
            return "redirect: /projects";
        }
        model.addAttribute("page", "projects");
        return "index";
    }

    @RequestMapping(value="/projects")
    public String serveProjects(ModelMap model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("page", "projects");
        return "projects";
    }

}

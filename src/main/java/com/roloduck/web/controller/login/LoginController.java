package com.roloduck.web.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

@Controller
public class LoginController {

    static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/login_error=1", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "redirect:/";
    }
}

package com.roloduck.web.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/22/14
 * RoloDuck
 */

@Controller
public class AccountController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String serveProfilePage(ModelMap model) {
        return "profile";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String serveSettingsPage(ModelMap model) {
        return "settings";
    }
}

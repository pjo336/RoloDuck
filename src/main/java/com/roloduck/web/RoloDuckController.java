package com.roloduck.web;

import com.roloduck.exception.BusinessLogicException;
import com.roloduck.user.converter.UserConverter;
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
 * @since 3/7/14
 * RoloDuck
 */

@Controller
public class RoloDuckController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/")
    public String serveIndex(ModelMap model, Principal principal) {
        if(principal != null) {
            String name = principal.getName(); //get logged in username
            model.addAttribute("user", name);
        }
        model.addAttribute("page", "projects");
        return "index";
    }

    @RequestMapping(value="/signup", method = RequestMethod.GET)
    public String serveSignup(ModelMap model) {
        return "signup";
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String signUpUser(@ModelAttribute("user") UserConverter userConverter, ModelMap model) {
        User newUser = new User(userConverter.getName(), userConverter.getEmail(), userConverter.getPassword());
        try {
            userService.signUpUser(newUser, userConverter.getCompanyIdentifier());
        } catch(BusinessLogicException ble) {
            // TODO figure out how to present exceptions
            ble.printStackTrace();
        }
        model.put("user", newUser);
        return "index";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value="/login_error=1", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "login";
    }
}

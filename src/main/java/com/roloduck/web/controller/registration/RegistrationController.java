package com.roloduck.web.controller.registration;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.user.User;
import com.roloduck.user.service.UserService;
import com.roloduck.web.converter.UserConverter;
import com.roloduck.web.exception.ProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/8/14
 * RoloDuck
 */

@Controller
public class RegistrationController extends ProcessException {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String serveSignup(ModelMap model) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUpUser(@ModelAttribute("user") UserConverter userConverter, ModelMap model) {
        User newUser = new User(userConverter.getName(), userConverter.getEmail(), userConverter.getPassword());
        try {
            userService.signUpUser(newUser, userConverter.getCompanyIdentifier());
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "signup";
        }
        model.put("user", newUser);
        return "index";
    }
}

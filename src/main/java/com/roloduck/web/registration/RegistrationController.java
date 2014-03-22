package com.roloduck.web.registration;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.user.converter.UserConverter;
import com.roloduck.user.model.User;
import com.roloduck.user.service.UserService;
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
public class RegistrationController {

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
        } catch(ServiceLogicException ble) {
            // TODO figure out how to present exceptions
            ble.printStackTrace();
        }
        model.put("user", newUser);
        return "index";
    }
}

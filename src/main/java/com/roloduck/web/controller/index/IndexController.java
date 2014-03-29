package com.roloduck.web.controller.index;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.user.User;
import com.roloduck.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/8/14
 * RoloDuck
 */

@Controller
public class IndexController {

    static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/")
    public String serveIndex(ModelMap model) {
        try {
            User currentUser = SecurityUtils.getCurrentUser();
            model.addAttribute("LoggedInUser", currentUser);
            logger.info("Current user is: " + currentUser.getEmail());
        } catch(ServiceLogicException e) {
            // A logged in user wasnt found, swallow this
            logger.info("An anonymousUser accessed the index page.");
        }
        model.addAttribute("page", "projects");
        return "index";
    }

}

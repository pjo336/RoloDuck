package com.roloduck.web.contact;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/21/14
 * RoloDuck
 */

@Controller
public class ContactController {

    private static final String URI_PREFIX = "/contacts";

    @RequestMapping(value = URI_PREFIX)
    public String serveContacts(ModelMap model) {
        return "contacts";
    }

}

package com.roloduck.web.partner;

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
public class PartnerController {

    private static final String URI_PREFIX = "/partners";

    @RequestMapping(value = URI_PREFIX)
    public String servePartners(ModelMap model) {
        return "partners";
    }
}

package com.roloduck.web.controller.index;

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
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping
    public String serveIndex(ModelMap model) {
        return "index";
    }
}

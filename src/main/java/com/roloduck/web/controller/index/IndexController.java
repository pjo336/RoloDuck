package com.roloduck.web.controller.index;

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
@RequestMapping(value = "/")
public class IndexController {

    static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping
    public String serveIndex(ModelMap model) {
        logger.info("Fetching index.");
        return "index";
    }
}

package com.roloduck.web.controller.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

@Controller
public class SearchController {

    static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping(value = "/seach", method = RequestMethod.POST)
    public String postSearch(ModelMap model) {
        System.out.println("derp");
        return "search";
    }
}

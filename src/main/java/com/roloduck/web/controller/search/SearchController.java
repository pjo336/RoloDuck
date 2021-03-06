package com.roloduck.web.controller.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

@Controller
public class SearchController {


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String postSearch(HttpServletRequest request, ModelMap model) {
        System.out.println(request.getParameter("searchTopic"));
        return "search";
    }
}

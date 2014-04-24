package com.roloduck.utils;

import com.google.gson.Gson;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 4/23/14
 * RoloDuck
 */

public class JSONUtils {

    /**
     * Private method to convert a result to the client into JSON format
     * @param result The result to the ajax call to convert to JSON
     * @param model Where to put the result
     * @throws IOException
     */
    public static void write(HttpServletResponseWrapper result, ModelMap model) throws IOException {
        result.setContentType("application/json");
        result.setCharacterEncoding("UTF-8");
        result.getWriter().write(new Gson().toJson(model));
    }
}

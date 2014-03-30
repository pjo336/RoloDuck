package com.roloduck.web.exception;

import com.roloduck.exception.RoloDuckException;
import org.springframework.ui.ModelMap;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/29/14
 * RoloDuck
 */

public class ProcessException {

    public ModelMap processRDException(ModelMap model, RoloDuckException e) {
        model.put("exceptions", e);
        return model;
    }
}

package com.roloduck.utils;

import com.roloduck.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/9/14
 * RoloDuck
 */

public class SecurityUtils {

    /**
     * Returns the currently logged in user, or returns anonymousUser if not logged in
     * @return user
     */
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Returns the currently logged in users name, or returns anonymousUser if not logged in
     * @return users name
     */
    public static String getCurrentUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if(auth == null) {
            return null;
        } else {
            return auth.getName();
        }
    }
}

package com.roloduck.utils;

import com.roloduck.exception.NotFoundException;
import com.roloduck.user.model.User;
import com.roloduck.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/9/14
 * RoloDuck
 */

@Component
public class SecurityUtils {

    @Autowired
    private UserService userService;

    static UserService userServiceStatic;

    @Autowired
    public void setUserServiceStatic(UserService userService) {
        SecurityUtils.userServiceStatic = userService;
    }
    /**
     * Returns the currently logged in user, or returns anonymousUser if not logged in
     * @return user
     */
    public static User getCurrentUser() throws NotFoundException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if(auth == null) {
            return null;
        } else {
            return userServiceStatic.findUserByEmail(auth.getName());
        }
    }

    /**
     * Returns the currently logged in users name, or returns anonymousUser if not logged in
     * @return users name
     */
    public static String getCurrentUserEmail() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if(auth == null) {
            return null;
        } else {
            return auth.getName();
        }
    }
}

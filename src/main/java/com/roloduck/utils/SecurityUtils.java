package com.roloduck.utils;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.user.model.User;
import com.roloduck.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    static UserService userServiceStatic;

    @Autowired
    public void setUserServiceStatic(UserService userService) {
        SecurityUtils.userServiceStatic = userService;
    }

    /**
     * Returns the currently logged in user, or returns anonymousUser if not logged in
     * @return user
     */
    public static User getCurrentUser() throws ServiceLogicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            return null;
        } else {
            return userServiceStatic.restoreUserByEmail(auth.getName());
        }
    }

    /**
     * Returns the currently logged in users name, or returns anonymousUser if not logged in
     * @return users name
     */
    public static String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            return null;
        } else {
            return auth.getName();
        }
    }
}

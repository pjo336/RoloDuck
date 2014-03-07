package com.roloduck.security;

import com.roloduck.exception.NotFoundException;
import com.roloduck.user.dao.UserDAO;
import com.roloduck.user.dao.UserRoleDAO;
import com.roloduck.user.model.User;
import com.roloduck.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/6/14
 * RoloDuck
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;

    /**
     * Find the user with this login, and convert it to a UserDetails User, which is then used to authenticate
     * a login. Right now this is performed with magic "trues" for 4 fields. We may want to look into these and
     * further alter the Pickle.PP_User schema
     * @param login the email used to login into the site
     * @return the user if he has been found, else null is returned
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        // Find the user by the given login parameter
        // This is currently the users email address
        User user = userDAO.findByEmail(login);
        UserRole role = null;
        try {
            role = userRoleDAO.restoreByUserId(user.getId());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        if(user != null) {
            // We have our user, so lets get his role and send him on to be authenticated
            Collection<? extends GrantedAuthority> grantedAuthorities = getRole(role);
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    grantedAuthorities);
        }
        throw new UsernameNotFoundException("This user was not found");
    }

    /**
     * This returns the role assigned to the user given. Right now each user can only have one role,
     * but since this returns a collection, it would be easy to implement multiple roles
     * @param role the role stored corresponding to the user being logged in
     * @return a list of all roles the current user logging in has
     */
    public Collection<? extends GrantedAuthority> getRole(UserRole role) {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(role.getAuthority()));
        return list;
    }
}

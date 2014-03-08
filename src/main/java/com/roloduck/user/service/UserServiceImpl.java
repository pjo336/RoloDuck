package com.roloduck.user.service;

import com.roloduck.exception.BusinessLogicException;
import com.roloduck.exception.NotFoundException;
import com.roloduck.models.company.dao.CompanyDAO;
import com.roloduck.security.Authority;
import com.roloduck.user.dao.UserDAO;
import com.roloduck.user.dao.UserRoleDAO;
import com.roloduck.user.model.User;
import com.roloduck.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private BCryptPasswordEncoder encoder; // Used to hash new users passwords

    @Override
    public void signUpUser(User user, String companyIdentifier) throws BusinessLogicException {
        if(user != null) {
            // Make sure the company exists
            try {
                long companyId = companyDAO.restoreCompanyByIdentifier(companyIdentifier).getId();
                // Hash the users password
                user.setPassword(encoder.encode(user.getPassword()));
                user.setCompanyId(companyId);
                // TODO connect user roles into the user object
                userDAO.insert(user);
                // Create and add the matching user role entry
                UserRole role = new UserRole(user.getId(), Authority.ROLE_USER);
                userRoleDAO.insert(role);
            } catch(NotFoundException nfe) {
                throw new BusinessLogicException("Invalid company identifier: " + companyIdentifier + " given.");
            } catch(Exception e) {
                e.printStackTrace();
                throw new BusinessLogicException("There was a problem signing up the user: " + user + ".");
            }
        }
    }

    @Override
    public User restoreUserById(long id) throws NotFoundException {
        User user = userDAO.restoreById(id);
        if(user != null) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    public List<User> findUserByEmail(String email) {
        // TODO
        return null;
    }

    @Override
    public void updateUser(User user) {
        if(user != null) {
            userDAO.updateUser(user);
        }
    }

    @Override
    public void removeUser(User user) {
        if(user != null) {
            userDAO.removeUser(user);
        }
    }
}

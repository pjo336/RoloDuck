package com.roloduck.user.service;

import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.dao.CompanyDAO;
import com.roloduck.security.Authority;
import com.roloduck.user.dao.UserDAO;
import com.roloduck.user.dao.UserRoleDAO;
import com.roloduck.user.model.User;
import com.roloduck.user.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserRoleDAO userRoleDAO;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private BCryptPasswordEncoder encoder; // Used to hash new users passwords

    @Override
    public void signUpUser(User user, String companyIdentifier) throws ServiceLogicException {
        if(user != null) {
            // Make sure the company exists
            try {
                long companyId = companyDAO.restoreCompanyByIdentifier(companyIdentifier).getId();
                // Hash the users password
                user.setPassword(encoder.encode(user.getPassword()));
                user.setCompanyId(companyId);
                userDAO.insert(user);
                // Create and add the matching user role entry
                UserRole role = new UserRole(user.getId(), Authority.ROLE_USER);
                userRoleDAO.insert(role);
            } catch(DAOException nfe) {
                logger.warn("The company identifier provided: " + companyIdentifier + " in signUpUser does not exist.");
                throw new ServiceLogicException("Invalid company identifier: " + companyIdentifier + " given.");
            } catch(Exception e) {
                logger.warn("There was a problem signing up the user: " + user + ". MESSAGE: " + e.getMessage());
                throw new ServiceLogicException("There was a problem signing up the user: " + user + ".");
            }
        }
    }

    @Override
    public User restoreUserById(long id) throws ServiceLogicException{
        try {
            User user = userDAO.restoreById(id);
            if(user != null) {
                return user;
            }
        } catch(DAOException e) {
            logger.warn("User with ID: " + id + " not found.");
            throw new ServiceLogicException(e);
        }
        logger.warn("There was a problem restoring a user by ID: " + id);
        throw new ServiceLogicException("There was a problem restoring a user by ID: " + id);
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.find();
    }

    @Override
    public User restoreUserByEmail(String email) throws ServiceLogicException {
        try{
            return userDAO.restoreByEmail(email);
        } catch(DAOException e) {
            logger.warn("User with EMAIL: " + email + " not found.");
            throw new ServiceLogicException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        if(user != null) {
            userDAO.updateOrStore(user);
        }
    }

    @Override
    public void removeUser(User user) {
        if(user != null) {
            userDAO.remove(user);
        }
    }
}

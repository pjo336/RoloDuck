package com.roloduck.user.service;

import com.roloduck.exception.BusinessLogicException;
import com.roloduck.exception.NotFoundException;
import com.roloduck.user.model.User;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public interface UserService {

    /**
     * Sign up a new user, and assign a unique login_hash before adding to the database.
     * The company identifier is used to tie the User to a particular company
     * @param user the user to be added
     * @param companyIdentifier a generated identifier of a company, if this doesnt exist, the user
     * cannot be added
     */
    public void signUpUser(User user, String companyIdentifier) throws BusinessLogicException;

    /**
     * Return the user with the given id. Throw exception if no user is found
     * @param id a user id
     * @return the user with the given id
     * @exception NotFoundException
     */
    public User restoreUserById(long id) throws NotFoundException;

    /**
     * Return all users in the database
     * @return a list of all users in the database
     */
    public List<User> findAllUsers();

    /**
     * Return a user with the given email address. Throw exception if no user is found.
     * @param email the users email address
     * @return the user with the given email
     * @exception com.roloduck.exception.NotFoundException
     */
    public User findUserByEmail(String email) throws NotFoundException;

    /**
     * If the user exists, update them to whatever is set in the user parameter, otherwise add the user to the database
     * @param user the user to update or insert
     */
    public void updateUser(User user);

    /**
     * If the user exists, remove them from the database. Otherwise, nothing happens
     * @param user the user to remove
     */
    public void removeUser(User user);
}

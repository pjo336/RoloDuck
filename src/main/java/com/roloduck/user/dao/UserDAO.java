package com.roloduck.user.dao;

import com.roloduck.exception.NotFoundException;
import com.roloduck.user.model.User;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public interface UserDAO {

    /**
     * Insert a user into the database
     * @param user a pickle payments user
     */
    public void insert(User user);

    /**
     * Find the user with the given id
     * @param id the id of the user to be found
     * @return the user with the given id
     * @exception com.roloduck.exception.NotFoundException
     */
    public User restoreById(Object id) throws NotFoundException;

    /**
     * Find the user with the given email. Note these should be unique, so only 1 should return
     * @param email the email of the user to be found
     * @return the user with the given email
     */
    public User findByEmail(String email);

    /**
     * Find all users
     * @return A list of all users
     */
    public List<User> findAllUsers();

    /**
     * If the user exists, update the record to reflect any changes,
     * otherwise insert the user
     * @param user the user to be updated
     */
    public void updateUser(User user);

    /**
     * If the user exists, delete the user, otherwise nothing happens.
     * @param user the user to be removed from the database
     */
    public void removeUser(User user);
}

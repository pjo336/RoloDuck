package com.roloduck.user.dao;

import com.roloduck.exception.DAOException;
import com.roloduck.user.User;

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
     * @param user a roloduck user
     */
    public void insertUser(User user);

    /**
     * Find the user with the given id
     * @param id the id of the user to be found
     * @return the user with the given id
     * @throws com.roloduck.exception.DAOException
     */
    public User restoreById(Object id) throws DAOException;

    /**
     * Find the user with the given email. Note these should be unique, so only 1 should return
     * @param email the email of the user to be found
     * @return the user with the given email
     * @throws com.roloduck.exception.DAOException
     */
    public User restoreByEmail(String email) throws DAOException;

    /**
     * Find all users
     * @return A list of all users
     */
    public List<User> find();

    /**
     * If the user exists, update the record to reflect any changes,
     * otherwise do nothing
     * @param user the user to be updated
     */
    public void update(User user);

    /**
     * If the user exists, delete the user, otherwise nothing happens.
     * @param user the user to be removed from the database
     */
    public void removeUser(User user);
}

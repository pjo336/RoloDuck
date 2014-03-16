package com.roloduck.user.dao;

import com.roloduck.exception.DAOException;
import com.roloduck.user.model.UserRole;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public interface UserRoleDAO {

    /**
     * Insert a matching UserRole when a User is created
     * @param role The UserRole object to be inserted along with User creation
     */
    public void insert(UserRole role);

    /**
     * Find the user role(s?) connected to the user with the given id
     * @param userId the id of the user whos role we are looking to find
     * @return the UserRole corresponding to the user
     * @exception com.roloduck.exception.DAOException
     */
    public UserRole restoreByUserId(long userId) throws DAOException;
}

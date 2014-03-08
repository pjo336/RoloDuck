package com.roloduck.user.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.NotFoundException;
import com.roloduck.user.model.UserRole;
import com.roloduck.user.model.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

@Repository
public class UserRoleDAOImpl extends RoloDuckEntityDAOImpl<UserRole> implements UserRoleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final String TABLE_NAME = "RD_USER_ROLES";

    @Override
    public void insert(UserRole role) {
        super.insert(role);
    }

    @Override
    public UserRole restoreByUserId(long userId) throws NotFoundException {
        final String SQL = "SELECT * FROM " + TABLE_NAME + " where user_id = ?";
        return jdbcTemplateObject.queryForObject(SQL,
                new Object[]{userId}, new UserRoleMapper());
    }
}

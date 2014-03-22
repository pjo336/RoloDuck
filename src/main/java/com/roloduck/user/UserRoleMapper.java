package com.roloduck.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public class UserRoleMapper implements RowMapper<UserRole> {

    @Override
    public UserRole mapRow(ResultSet resultSet, int i) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setId(resultSet.getLong("id"));
        userRole.setUserId(resultSet.getLong("user_id"));
        userRole.setAuthority(resultSet.getString("authority"));
        return userRole;
    }
}

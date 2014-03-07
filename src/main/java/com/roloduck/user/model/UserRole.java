package com.roloduck.user.model;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.security.Authority;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public class UserRole implements RoloDuckEntity {

    private static final String TABLE_NAME = "RD_USER_ROLES";

    private int id;
    private int userId;
    private String authority;

    public UserRole(){}

    public UserRole(int userId, Authority authority) {
        this.userId = userId;
        this.authority = authority.getStringValue();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"user_id", "authority"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final UserRole newRole = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setInt(1, newRole.getUserId());
                ps.setString(2, newRole.getAuthority());
                return ps;
            }
        };
    }

    @Override
    public RowMapper getEntityMapper() {
        return null;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority.value();
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
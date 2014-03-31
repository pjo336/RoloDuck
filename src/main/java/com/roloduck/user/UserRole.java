package com.roloduck.user;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.ServiceLogicException;
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

    private long id;
    private long userId;
    private String authority;

    public UserRole(){}

    public UserRole(long userId, Authority authority) {
        this.userId = userId;
        this.authority = authority.getStringValue();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getDistinctColumnNames() {
        return new String[]{"user_id", "authority"};
    }

    @Override
    public String[] getAllColumnNames() {
        return new String[]{"id", "user_id", "authority", "date_created", "date_modified"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final UserRole newRole = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setLong(1, newRole.getUserId());
                ps.setString(2, newRole.getAuthority());
                return ps;
            }
        };
    }

    @Override
    public RowMapper getEntityMapper() {
        return null;
    }

    @Override
    public void validate() throws ServiceLogicException {
        StringBuilder errors = new StringBuilder();
        if(getUserId() < 1) {
            errors.append("User Role needs a valid User attached.\n");
        }
        if(getAuthority() == null) {
            errors.append("User Role needs a valid Authority.\n");
        }
        if(errors.length() > 0) {
            throw new ServiceLogicException(errors.toString());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
package com.roloduck.user.model;

import com.roloduck.entity.RoloDuckEntity;
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

public class User implements RoloDuckEntity {

    private static final String TABLE_NAME = "RD_USER";

    private int id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;

    /**
     * Blank default constructor
     */
    public User() {}

    /**
     * Constructor
     * @param name the name of the user
     * @param email the email address of the user
     * @param password the password the user selected
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"user_name", "user_email", "user_password"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final User newUser = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setString(1, newUser.getName());
                ps.setString(2, newUser.getEmail());
                ps.setString(3, newUser.getPassword());
                return ps;
            }
        };
    }

    @Override
    public RowMapper<User> getEntityMapper() {
        return new UserMapper();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() { return userRole; }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


}

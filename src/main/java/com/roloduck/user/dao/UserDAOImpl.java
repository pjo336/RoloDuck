package com.roloduck.user.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.user.User;
import com.roloduck.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

@Repository
public class UserDAOImpl extends RoloDuckEntityDAOImpl<User> implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final String TABLE_NAME = "RD_USER";

    @Override
    public void insertUser(User user) {
        super.insert(user);
    }

    @Override
    public User restoreById(Object id) throws DAOException {
        return super.restoreById(id, new User());
    }

    @Override
    public User restoreByEmail(String email) throws DAOException {
        try {
            final String SQL = "SELECT * FROM " + TABLE_NAME + " where user_email = ?";
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{email}, new UserMapper());
        } catch(EmptyResultDataAccessException e) {
            throw new DAOException("User with EMAIL:  " + email + " was not found.", e);
        }
    }

    @Override
    public List<User> find() {
        return super.find(new User());
    }

    @Override
    public void update(User user) {
        final String SQL = "UPDATE " + TABLE_NAME + " SET user_name = ?, user_email = ?," +
                "user_password = ? where id = ?";
        jdbcTemplateObject.update(SQL,user.getName(), user.getEmail(),
                user.getPassword(), user.getId());
    }

    @Override
    public void removeUser(User user) {
        super.remove(user);
    }
}

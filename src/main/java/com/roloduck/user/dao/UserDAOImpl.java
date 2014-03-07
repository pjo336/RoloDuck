package com.roloduck.user.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.NotFoundException;
import com.roloduck.user.model.User;
import com.roloduck.user.model.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insert(User user) {
        super.insert(user);
    }

    @Override
    public User restoreById(Object id) throws NotFoundException {
        return super.restoreById(id, new User());
    }

    @Override
    public User findByEmail(String email) {
        final String SQL = "SELECT * FROM " + TABLE_NAME + " where user_email = ?";
        return jdbcTemplateObject.queryForObject(SQL,
                new Object[]{email}, new UserMapper());
    }

    @Override
    public List<User> findAllUsers() {
        return super.findAll(new User());
    }

    @Override
    public void updateUser(User user) {
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

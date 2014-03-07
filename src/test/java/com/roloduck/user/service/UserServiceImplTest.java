package com.roloduck.user.service;

import com.roloduck.exception.NotFoundException;
import com.roloduck.user.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService impl;

    private static String userName = "admin";
    private static String email = "admin@roloduck.com";
    private static String password = "admin";

    /**
     * This is an integration test involving interaction with the real db.
     * TODO: This should also test that a role was added to match the user
     * Test is now transactional, so the added user will be rolled back upon test completion
     */
    @Test
    public void testInsertUser() {
        try {
            int size = impl.findAllUsers().size();
            User newUser = new User(userName, email, password);
            // A new user that has not been added to the DB yet will have 0 as an id
            Assert.assertTrue(newUser.getId() == 0);
            // Add the user to the db
            impl.signUpUser(newUser);
            // Ensure that the user has an updated id
            Assert.assertFalse(newUser.getId() == 0);
            Assert.assertEquals(size + 1, impl.findAllUsers().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a user, then test restoreUserById to find this user.
     * @throws NotFoundException
     */
    @Test
    public void testFindUserById() throws NotFoundException {
        User newUser = new User(userName, email, password);
        impl.signUpUser(newUser);
        User foundUser = impl.restoreUserById(newUser.getId());
        Assert.assertNotNull(foundUser);
    }


    /**
     * Add a user then remove it. Test to make sure the remove method works assuming the insert works as tested
     * above.
     */
    @Test
    public void testRemoveUser() {
        int size = impl.findAllUsers().size();
        User newUser = new User(userName, email, password);
        impl.signUpUser(newUser);
        Assert.assertEquals(size + 1, impl.findAllUsers().size());
        impl.removeUser(newUser);
        Assert.assertEquals(size, impl.findAllUsers().size());
    }
}
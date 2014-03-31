package com.roloduck.user.dao;

import com.roloduck.IntegrationTest;
import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/30/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@IntegrationTest
@Transactional
public class UserDAOImplTest {

    @Autowired
    private UserDAO impl;

    @Autowired
    private CompanyService companyService;

    /**
     * Tests user insert functionality.
     * NOTE: Assumes the RoloDuck company is in the database
     * @throws ServiceLogicException
     */
    @Test
    public void testInsertUser() throws ServiceLogicException {
        User user = new User();
        user.setName("This is a test user name");
        user.setEmail("This is a test user email @email.com");
        user.setPassword("123");
        user.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        long countBefore = impl.count();
        // Make sure the id assignment works when a user is inserted
        assertTrue(user.getId() == 0);
        impl.insertUser(user);
        assertFalse(user.getId() == 0);
        long countAfter = impl.count();
        assertEquals("The amount of users after an insert is incorrect", countBefore + 1, countAfter);
    }

    /**
     * Tests that restoreById returns the User with all the correct information.
     * NOTE: this test assumes the RoloDuck company is in the database
     * @throws DAOException
     * @throws ServiceLogicException
     */
    @Test
    public void testRestoreById() throws DAOException, ServiceLogicException {
        User user = new User();
        user.setName("This is a test user name");
        user.setEmail("This is a test user email @email.com");
        user.setPassword("123");
        user.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        impl.insertUser(user);
        long userId = user.getId();
        User retrievedUser = impl.restoreById(userId);
        assertEquals("The retrieved users name is not the same as the original.", user.getName(), retrievedUser.getName());
        assertEquals("The retrieved users email is not the same as the original.", user.getEmail(), retrievedUser.getEmail());
        assertEquals("The retrieved users password is not the same as the original.", user.getPassword(),
                retrievedUser.getPassword());
        assertEquals("The retrieved users company id is not the same as the original.", user.getCompanyId(),
                retrievedUser.getCompanyId());
    }

    /**
     * Tests that a DAOException is thrown when restoreById does not return anything
     * @throws DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreByIdNotFound() throws DAOException {
        impl.restoreById(-999);
    }

    /**
     * Tests that when restoreById is called on a null object, a correct exception is thrown
     * @throws DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreByIdNull() throws DAOException {
        impl.restoreById(null);
    }

    /**
     * Tests that restoreByEmail returns the User with all the correct information.
     * NOTE: this test assumes the RoloDuck company is in the database
     * @throws DAOException
     * @throws ServiceLogicException
     */
    @Test
    public void testRestoreByEmail() throws DAOException, ServiceLogicException {
        User user = new User();
        user.setName("This is a test user name");
        user.setEmail("This is a test user email @email.com");
        user.setPassword("123");
        user.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        impl.insertUser(user);
        String userEmail = user.getEmail();
        User retrievedUser = impl.restoreByEmail(userEmail);
        assertEquals("The retrieved users name is not the same as the original.", user.getName(), retrievedUser.getName());
        assertEquals("The retrieved users email is not the same as the original.", user.getEmail(), retrievedUser.getEmail());
        assertEquals("The retrieved users password is not the same as the original.", user.getPassword(),
                retrievedUser.getPassword());
        assertEquals("The retrieved users company id is not the same as the original.", user.getCompanyId(),
                retrievedUser.getCompanyId());
    }

    /**
     * Tests that a DAOException is thrown when restoreByEmail does not return anything
     * @throws DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreByEmailNotFound() throws DAOException {
        // Hopefully this is a random enough string to test not found on
        impl.restoreByEmail(UUID.randomUUID().toString());
    }

    /**
     * Test that the find method returns all the users in the db. This means the count should equal the size of the
     * returned list.
     * NOTE: this test assumes the RoloDuck company is in the database
     * @throws ServiceLogicException
     */
    @Test
    public void testFind() throws ServiceLogicException {
        User user = new User();
        user.setName("This is a test user name");
        user.setEmail("This is a test user email @email.com");
        user.setPassword("123");
        user.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        impl.insertUser(user);
        long amountOfUsers = impl.count();
        assertEquals("The list doesnt contain all the users.", amountOfUsers, impl.find().size());
    }

    /**
     * Tests the updateUser method updates data for a particular user in the database.
     * NOTE: this test assumes the RoloDuck company is in the database
     * @throws ServiceLogicException
     * @throws DAOException
     */
    @Test
    public void testUpdateUser() throws ServiceLogicException, DAOException {
        User user = new User();
        user.setName("This is a test user name");
        user.setEmail("This is a test user email @email.com");
        user.setPassword("123");
        user.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        impl.insertUser(user);
        long userId = user.getId();
        User retrievedUser = impl.restoreById(userId);
        assertEquals("The retrieved users name is not the same as the original.", user.getName(), retrievedUser.getName());
        assertEquals("The retrieved users email is not the same as the original.", user.getEmail(), retrievedUser.getEmail());
        assertEquals("The retrieved users password is not the same as the original.", user.getPassword(),
                retrievedUser.getPassword());
        assertEquals("The retrieved users company id is not the same as the original.", user.getCompanyId(),
                retrievedUser.getCompanyId());

        // Now update the user
        String newName = "This is the updated users name.";
        String newPassword = "This is the updated users password.";
        user.setName(newName);
        user.setPassword(newPassword);
        impl.update(user);

        // Ensure the updated values and the original values are present on the retrieved user
        User updatedUser = impl.restoreById(userId);
        assertEquals("The retrieved users name is not the same as the original.", newName, updatedUser.getName());
        assertEquals("The retrieved users email is not the same as the original.", user.getEmail(), updatedUser.getEmail());
        assertEquals("The retrieved users password is not the same as the original.", newPassword,
                updatedUser.getPassword());
        assertEquals("The retrieved users company id is not the same as the original.", user.getCompanyId(),
                updatedUser.getCompanyId());
    }

    /**
     * Tests that when a user is removed, the total count of the users drops by 1
     * NOTE: this test assumes the RoloDuck company is in the database
     * @throws ServiceLogicException
     */
    @Test
    public void testRemoveUser() throws ServiceLogicException {
        User user = new User();
        user.setName("This is a test user name");
        user.setEmail("This is a test user email @email.com");
        user.setPassword("123");
        user.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        impl.insertUser(user);
        long countBeforeRemoval = impl.count();
        impl.removeUser(user);
        long countAfterRemoval = impl.count();
        assertEquals(countBeforeRemoval - 1, countAfterRemoval);
    }

    /**
     * Tests that nothing occurs when a removeUser is called on a null user
     */
    @Test
    public void testRemoveUserNull() {
        impl.removeUser(null);
    }

}

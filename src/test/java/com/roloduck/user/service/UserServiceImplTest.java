package com.roloduck.user.service;

import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.model.Company;
import com.roloduck.models.company.model.SubscriptionType;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.user.dao.UserRoleDAO;
import com.roloduck.user.model.User;
import com.roloduck.user.model.UserRole;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserRoleDAO userRoleDAO;

    // Our expected exception rule
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private static String userName = "wharrrbllllll";
    private static String email = "wharrrbllllll";
    private static String password = "wharrrbllllll";

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
            // We need a company for the user to match
            Company newCompany = new Company("name", SubscriptionType.FREE_SUBSCRIPTION);
            companyService.createCompany(newCompany);
            // Add the user to the db
            impl.signUpUser(newUser, newCompany.getCompanyIdentifyingString());
            // Ensure that the user has an updated id
            Assert.assertFalse(newUser.getId() == 0);
            Assert.assertEquals(size + 1, impl.findAllUsers().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a user, then test restoreUserById to find this user.
     * @throws com.roloduck.exception.DAOException
     */
    @Test
    public void testRestoreUserById() throws DAOException {
        try {
            User newUser = new User(userName, email, password);
            // We need a company for the user to match
            Company newCompany = new Company("name", SubscriptionType.FREE_SUBSCRIPTION);
            companyService.createCompany(newCompany);
            impl.signUpUser(newUser, newCompany.getCompanyIdentifyingString());
            User foundUser = impl.restoreUserById(newUser.getId());
            Assert.assertNotNull(foundUser);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that a service logic exception is thrown when a user is not found by the given id
     * @throws ServiceLogicException
     */
    @Test
    public void testFindUserByIdNotFound() throws ServiceLogicException {
        thrown.expect(ServiceLogicException.class);
        impl.restoreUserById(-999);
    }

    /**
     * Test finding a users role by the users id
     * @throws com.roloduck.exception.DAOException
     */
    @Test
    public void testFindUserRoleByUserId() throws DAOException {
        User newUser = new User(userName, email, password);
        // We need a company for the user to match
        Company newCompany = new Company("name", SubscriptionType.FREE_SUBSCRIPTION);
        try {
            companyService.createCompany(newCompany);
            impl.signUpUser(newUser, newCompany.getCompanyIdentifyingString());
        } catch(ServiceLogicException ble) {
            ble.printStackTrace();
        }
        UserRole role = userRoleDAO.restoreByUserId(newUser.getId());
        System.out.println(role);
    }

    /**
     * Test that updating a users name actually updates it.
     */
    @Test
    public void testUpdateUser() {
        User newUser = new User(userName, email, password);
        // We need a company for the user to match
        Company newCompany = new Company("name", SubscriptionType.FREE_SUBSCRIPTION);
        try {
            companyService.createCompany(newCompany);
            impl.signUpUser(newUser, newCompany.getCompanyIdentifyingString());
        } catch(ServiceLogicException ble) {
            ble.printStackTrace();
        }
        // Now the test!
        String updatedName = "This is my updated Name!";
        newUser.setName(updatedName);
        impl.updateUser(newUser);
        // Now read the user back from the database and ensure it has the updated name
        try {
            User updatedUser = impl.restoreUserById(newUser.getId());
            Assert.assertEquals("The updated name and original name of the user were not equal",
                    updatedName, updatedUser.getName());
        } catch (ServiceLogicException sle) {
            sle.printStackTrace();
        }
    }

    /**
     * Test that when an update is called on a user that does not exist, it is inserted
     */
    @Test
    public void testUpdateUserThatDoesNotExist() {
        // TODO
        User newUser = new User(userName, email, password);
        //Assert.assertTrue();
    }

    /**
     * Add a user then remove it. Test to make sure the remove method works assuming the insert works as tested
     * above.
     */
    @Test
    public void testRemoveUser() {
        int size = impl.findAllUsers().size();
        User newUser = new User(userName, email, password);
        // We need a company for the user to match
        Company newCompany = new Company("name", SubscriptionType.FREE_SUBSCRIPTION);
        try {
            companyService.createCompany(newCompany);
            impl.signUpUser(newUser, newCompany.getCompanyIdentifyingString());
        } catch(ServiceLogicException ble) {
            ble.printStackTrace();
        }
        Assert.assertEquals("The incremented size after adding a user was not correct",
                    size + 1, impl.findAllUsers().size());
        impl.removeUser(newUser);
        Assert.assertEquals(size, impl.findAllUsers().size());
    }
}
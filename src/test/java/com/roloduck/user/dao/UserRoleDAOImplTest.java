package com.roloduck.user.dao;

import com.roloduck.IntegrationTest;
import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.security.Authority;
import com.roloduck.user.User;
import com.roloduck.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

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
public class UserRoleDAOImplTest {

    @Autowired
    private UserRoleDAO impl;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CompanyService companyService;

    /**
     * Tests the insert method for UserRoles. The count should raise by one.
     * NOTE: Assumes the RoloDuck company is in the database
     * @throws ServiceLogicException
     */
    @Test
    public void testInsert() throws ServiceLogicException {
        long countBefore = impl.count();
        User user = createUser("This is a test name.", "this is a test email.", "This is a test password");
        userDAO.insertUser(user);
        UserRole role = new UserRole(user.getId(), Authority.ROLE_AUTHORITY);
        impl.insert(role);
        long countAfter = impl.count();
        assertEquals("The amount of roles is incorrect.", countBefore + 1, countAfter);
    }

    /**
     * Tests that nothing happens, as expected, when a null entity is inserted.
     */
    @Test
    public void testInsertNull() {
        impl.insert(null);
    }

    /**
     * Tests that restoring a role by the user id finds the correct role row
     * NOTE: Assumes the RoloDuck company is in the database
     * @throws ServiceLogicException
     * @throws DAOException
     */
    @Test
    public void testRestoreByUserId() throws ServiceLogicException, DAOException {
        User user = createUser("This is a test name.", "this is a test email.", "This is a test password");
        userDAO.insertUser(user);
        UserRole role = new UserRole(user.getId(), Authority.ROLE_AUTHORITY);
        impl.insert(role);
        UserRole retrievedRole = impl.restoreByUserId(user.getId());
        assertEquals(role.getUserId(), retrievedRole.getUserId());
        assertEquals(role.getAuthority(), retrievedRole.getAuthority());
    }

    /**
     * Tests that a DAOException is thrown when a role is not found by user id
     * @throws DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreByUserIdNotFound() throws DAOException {
        impl.restoreByUserId(-999);
    }

    /**
     * Create a user for testing purposes
     * NOTE: Assumes the RoloDuck company is in the database
     * @param name the name of the test user
     * @param email the email of the test user
     * @param password the password of the test user
     * @return the created testable User
     * @throws ServiceLogicException
     */
    private User createUser(String name, String email, String password) throws ServiceLogicException{
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        return user;
    }
}

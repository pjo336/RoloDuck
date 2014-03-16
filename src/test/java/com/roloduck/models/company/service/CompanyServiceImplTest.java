package com.roloduck.models.company.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.model.Company;
import com.roloduck.models.company.model.SubscriptionType;
import com.roloduck.user.model.User;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@Transactional
public class CompanyServiceImplTest {

    @Autowired
    private CompanyService impl;

    // Our expected exception rule
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private static final String name = UUID.randomUUID().toString();
    private static final SubscriptionType subType = SubscriptionType.PREMIUM_SUBSCRIPTION;

    /**
     * Tests creating a new company, checking if the company name exists (it should not)
     */
    @Test
    public void testCreateCompany() throws ServiceLogicException {
        Company newCompany = new Company(name, subType);
        impl.createCompany(newCompany);
    }

    /**
     * Test proper exception is thrown when an attempt is made to create a null company
     */
    @Test
    public void testCreateNullCompany() throws ServiceLogicException {
        thrown.expect(ServiceLogicException.class);
        impl.createCompany(null);
    }

    /**
     * Test that an exception is correctly thrown when a company name is already taken
     */
    @Test
    public void testCompanyAlreadyExists() throws ServiceLogicException {
        Company newCompany = new Company(name, subType);
        Company newCompany2 = new Company(name, subType);
        impl.createCompany(newCompany);
        // This should throw an exception because the company name is already user
        thrown.expect(ServiceLogicException.class);
        impl.createCompany(newCompany2);
    }

    /**
     * Test that finding a company by its id works
     * @throws ServiceLogicException
     */
    @Test
    public void testFindCompanyById() throws ServiceLogicException {
        Company newCompany = new Company(name, subType);
        impl.createCompany(newCompany);
        long id = newCompany.getId();
        Company foundCompany = impl.restoreCompanyById(id);
        // Make sure our two company items are not the exact same reference
        Assert.assertFalse("The new company and found company are the same objects", newCompany == foundCompany);
        Assert.assertEquals(newCompany.getCompanyName(), foundCompany.getCompanyName());
        Assert.assertEquals(newCompany.getSubscriptionType(), foundCompany.getSubscriptionType());
    }

    /**
     * Test that restoreCompanyById throws a ServiceLogicException if the company is not found
     * @throws ServiceLogicException
     */
    @Test
    public void testFindCompanyByIdNotFound() throws ServiceLogicException {
        thrown.expect(ServiceLogicException.class);
        impl.restoreCompanyById(-999);
    }

    /**
     * Test that a company can be found by being given a user linked to the company
     */
    @Test
    public void testFindCompanyByUser() throws ServiceLogicException {
        Company newCompany = new Company(name, subType);
        impl.createCompany(newCompany);
        User user = new User();
        user.setCompanyId(newCompany.getId());
        Company foundCompany = impl.restoreCompanyByUser(user);
        // Make sure our two company items are not the exact same reference
        Assert.assertFalse("The new company and found company are the same objects", newCompany == foundCompany);
        Assert.assertEquals(newCompany.getCompanyName(), foundCompany.getCompanyName());
        Assert.assertEquals(newCompany.getSubscriptionType(), foundCompany.getSubscriptionType());
    }
    /**
     * Test that when a null user is given to restoreCompanyByUser, the result is null
     * @throws ServiceLogicException
     */
    @Test
    public void testFindingNullUserCompany() throws ServiceLogicException {
        Assert.assertEquals(null, impl.restoreCompanyByUser(null));
    }

    /**
     * Test that a service logic exception is thrown when we search for a company based on a user that doesnt have
     * a company.
     * @throws ServiceLogicException
     */
    @Test
    public void testFindingNonExistentUserCompany() throws ServiceLogicException {
        User user = new User();
        thrown.expect(ServiceLogicException.class);
        impl.restoreCompanyByUser(user);
    }

}

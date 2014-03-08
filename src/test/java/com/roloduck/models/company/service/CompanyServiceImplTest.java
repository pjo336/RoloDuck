package com.roloduck.models.company.service;

import com.roloduck.exception.BusinessLogicException;
import com.roloduck.models.company.model.Company;
import com.roloduck.models.company.model.SubscriptionType;
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


    private static final String name = UUID.randomUUID().toString();
    private static final SubscriptionType subType = SubscriptionType.PREMIUM_SUBSCRIPTION;

    /**
     * Tests creating a new company, checking if the company name exists (it should not)
     */
    @Test
    public void testCreateCompany() throws BusinessLogicException {
        Company newCompany = new Company(name, subType);
        impl.createCompany(newCompany);
    }

    /**
     * Test that an exception is correctly thrown when a company name is already taken
     */
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    @Test
    public void testCompanyAlreadyExists() throws BusinessLogicException {
        Company newCompany = new Company(name, subType);
        Company newCompany2 = new Company(name, subType);
        impl.createCompany(newCompany);
        // This should throw an exception because the company name is already user
        thrown.expect(BusinessLogicException.class);
        impl.createCompany(newCompany2);
    }

}

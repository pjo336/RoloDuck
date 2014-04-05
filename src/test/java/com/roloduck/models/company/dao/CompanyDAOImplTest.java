package com.roloduck.models.company.dao;

import com.roloduck.IntegrationTest;
import com.roloduck.exception.DAOException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.SubscriptionType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@IntegrationTest
@Transactional
public class CompanyDAOImplTest {

    @Autowired
    CompanyDAO impl;

    // Our expected exception rule
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    /**
     * A simple test of insert company
     */
    @Test
    public void testInsertCompany() {
        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insertCompany(company);
    }

    /**
     * Test that calling insert on null wont break anything
     */
    @Test
    public void testInsertNullCompany() {
        impl.insertCompany(null);
    }

    /**
     * Test restoreById returns the correct company
     * @throws DAOException
     */
    @Test
    public void testRestoreById() throws DAOException {
        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insertCompany(company);
        long id = company.getId();

        Company foundCompany = impl.restoreById(id);
        assertEquals("Restored company didnt match original", company.getCompanyName(), foundCompany.getCompanyName());
        assertEquals("Restored company didnt match original", company.getCompanyIdentifyingString(),
                foundCompany.getCompanyIdentifyingString());
        assertEquals("Restored company didnt match original", company.getSubscriptionType(), foundCompany.getSubscriptionType());
    }

    /**
     * Test restoreCompanyByName
     * @exception com.roloduck.exception.DAOException
     */
    @Test
    public void testRestoreCompanyByName() throws DAOException {
        String companyName = "This is a test company sjfaksjaksfkjskfsfa";
        Company company = new Company(companyName,
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insertCompany(company);

        Company foundCompany = impl.restoreCompanyByName(companyName);
        assertEquals("Restored company didnt match original", company.getCompanyName(), foundCompany.getCompanyName());
        assertEquals("Restored company didnt match original", company.getCompanyIdentifyingString(),
                foundCompany.getCompanyIdentifyingString());
        assertEquals("Restored company didnt match original", company.getSubscriptionType(), foundCompany.getSubscriptionType());
    }

    /**
     * Test that a DAOException is properly thrown when nothing is returned by restoreCompanyByName
     * @exception com.roloduck.exception.DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreCompanyByNameNotFound() throws DAOException {
        impl.restoreCompanyByName(UUID.randomUUID().toString());
    }

    /**
     * Test there is no problem when restoreCompanyByName is called with a null company
     * @exception com.roloduck.exception.DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreCompanyByNameNull() throws DAOException {
        impl.restoreCompanyByName(null);
    }

    /**
     * Test restoreCompanyByIdentifier
     * @exception com.roloduck.exception.DAOException
     */
    @Test
    public void testRestoreCompanyByIdentifier() throws DAOException {
        String companyName = "This is a test company sjfaksjaksfkjskfsfa";
        String companyIdentifier = "company identifying string for testing";
        Company company = new Company(companyName,
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(companyIdentifier);
        impl.insertCompany(company);

        Company foundCompany = impl.restoreCompanyByIdentifier(companyIdentifier);
        assertEquals("Restored company didnt match original", company.getCompanyName(), foundCompany.getCompanyName());
        assertEquals("Restored company didnt match original", company.getCompanyIdentifyingString(),
                foundCompany.getCompanyIdentifyingString());
        assertEquals("Restored company didnt match original", company.getSubscriptionType(), foundCompany.getSubscriptionType());
    }

    /**
     * Test that a DAOException is properly thrown when nothing is returned by restoreCompanyByIdentifier
     * @exception com.roloduck.exception.DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreCompanyByIdentifierNotFound() throws DAOException {
        impl.restoreCompanyByIdentifier(UUID.randomUUID().toString());
    }

    /**
     * Test there is no problem when restoreCompanyByIdentifier is called with a null company
     * @exception com.roloduck.exception.DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreCompanyByIdentifierNull() throws DAOException {
        impl.restoreCompanyByIdentifier(null);
    }

    /**
     * Test that the exception type thrown by our abstract DAO is correctly morphed into a DAOException
     * @throws DAOException
     */
    @Test
    public void testExceptionTypeOnEmptyDataReturn() throws DAOException {
        thrown.expect(DAOException.class);
        try {
            impl.restoreById(-1);
        } catch(DAOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * A quick test of the company find method
     */
    @Test
    public void testFind() {
        long count = impl.count();

        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insertCompany(company);

        Company company2 = new Company("This is a test company2 sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company2.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insertCompany(company2);

        List<Company> allCompanies = impl.find();
        assertEquals("Find returned a list of the wrong size", count + 2, allCompanies.size());

        boolean company1Seen = false;
        boolean company2Seen = false;
        for(Company c: allCompanies) {
            if(c.getCompanyIdentifyingString().equalsIgnoreCase(company.getCompanyIdentifyingString())) {
                company1Seen = true;
            }
            if(c.getCompanyIdentifyingString().equalsIgnoreCase(company2.getCompanyIdentifyingString())) {
                company2Seen = true;
            }
        }
        assertTrue(company1Seen);
        assertTrue(company2Seen);
    }
}

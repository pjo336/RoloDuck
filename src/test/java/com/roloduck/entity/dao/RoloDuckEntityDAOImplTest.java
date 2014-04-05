package com.roloduck.entity.dao;

import com.roloduck.IntegrationTest;
import com.roloduck.exception.DAOException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.SubscriptionType;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.partner.Partner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 4/5/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@IntegrationTest
@Transactional
@SuppressWarnings("unchecked")
public class RoloDuckEntityDAOImplTest {

    /*
    NOTE: Since these methods are generic, they will be tested using different RoloDuckEntities.
    The generics are also why we are suppressing unchecked warnings, as they will be thrown everywhere.
     */

    @Resource(name="roloDuckEntityDAOImpl")
    private RoloDuckEntityDAOImpl impl;

    /**
     * Test that nothing bad happens when calling insert on a null object.
     */
    @Test
    public void testInsertNull() {
        impl.insert(null);
    }

    /**
     * Test the insert method using a Company for simplicity
     */
    @Test
    public void testInsert() {
        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insert(company);
    }

    /**
     * Test the restoreById method using a Company
     * @throws DAOException
     */
    @Test
    public void testRestoreById() throws DAOException {
        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insert(company);
        long id = company.getId();

        Company foundCompany = (Company) impl.restoreById(id, new Company());
        assertEquals("Restored company didnt match original", company.getCompanyName(), foundCompany.getCompanyName());
        assertEquals("Restored company didnt match original", company.getCompanyIdentifyingString(),
                foundCompany.getCompanyIdentifyingString());
        assertEquals("Restored company didnt match original", company.getSubscriptionType(), foundCompany.getSubscriptionType());
    }

    /**
     * Test that when the id object is null in a restoreById call, that a DAOException is correctly thrown
     * @throws DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreByIdNullObject() throws DAOException {
        impl.restoreById(null, new Company());
    }

    /**
     * Test that when the entity is null in a restoreById call, that a DAOException is correctly thrown
     * @throws DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreByIdNullEntity() throws DAOException {
        impl.restoreById(null, new Company());
    }

    /**
     * Test that when nothing is returned by restoreById, the EmptyDataAccess exception is correctly caught and
     * converted into a DAOException.
     * NOTE: The exception should contain the name of the class, which in this case is a Company
     * @throws DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreByIdEmptyDataCompany() throws DAOException {
        try {
            impl.restoreById(-999, new Company());
        } catch(DAOException dae) {
            if(!dae.getMessage().contains("Company")) {
                fail();
            }
            throw dae;
        }
    }

    /**
     * Test that when nothing is returned by restoreById, the EmptyDataAccess exception is correctly caught and
     * converted into a DAOException.
     * NOTE: The exception should contain the name of the class, which in this case is a Partner
     * @throws DAOException
     */
    @Test(expected = DAOException.class)
    public void testRestoreByIdEmptyDataPartner() throws DAOException {
        try {
            impl.restoreById(-999, new Partner());
        } catch(DAOException dae) {
            if(!dae.getMessage().contains("Partner")) {
                fail();
            }
            throw dae;
        }
    }

    /**
     * A quick test of the find method using a company
     */
    @Test
    public void testFindCompany() {
        long count = impl.count(new Company());

        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insert(company);

        Company company2 = new Company("This is a test company2 sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company2.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insert(company2);

        List<Company> allCompanies = impl.find(new Company());
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

    /**
     * A quick test of the find method using a Partner. Same as the Company test, just for posterity of testing the
     * generics
     */
    @Test
    public void testFindPartner() {
        // Get the count
        long count = impl.count(new Partner());

        // Create the company we need to attach to a Partner
        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insert(company);

        // Now create the partner and test the find
        Partner partner = new Partner();
        partner.setPartnerName("This is a test partnerName kflaufieugiw");
        partner.setCompanyId(company.getId());
        impl.insert(partner);

        List<Partner> allPartners = impl.find(new Partner());
        assertEquals("Find returned a list of the wrong size", count + 1, allPartners.size());

        boolean partner1Seen = false;
        for(Partner p: allPartners) {
            if(p.getPartnerName().equalsIgnoreCase(partner.getPartnerName())) {
                partner1Seen = true;
            }
        }
        assertTrue(partner1Seen);
    }

    /**
     * Test that there are no issues with a call to find finding nothing
     */
    @Test
    public void testFindNotFound() {
        // TODO make a better test for this, removing all contacts would be incredibly stupid when there's many
        List<Contact> contacts = impl.find(new Contact());
        for(Contact contact: contacts) {
            impl.remove(contact);
        }
        assertTrue(0 == impl.find(new Contact()).size());
    }

    /**
     * Test that a call to find with a null entity both returns null and does not throw an exception
     */
    @Test
    public void testFindNull() {
        assertNull(impl.find(null));
    }

    /**
     * Test that remove correctly removes an object from the database
     */
    @Test
    public void testRemove() {
        // Create the company we need to attach to a Partner
        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insert(company);

        // Now create the partner and test the find
        Partner partner = new Partner();
        partner.setPartnerName("This is a test partnerName kflaufieugiw");
        partner.setCompanyId(company.getId());
        impl.insert(partner);

        // Now get the count, remove the partner and make sure the count is -1
        long countBeforeRemove = impl.count(new Partner());
        impl.remove(partner);
        long countAfterRemove = impl.count(new Partner());

        assertEquals(countBeforeRemove - 1, countAfterRemove);
    }

    /**
     * Test that when remove is called on an entity that isnt stored in the database,
     * nothing is changed and no exception is thrown.
     */
    @Test
    public void testRemoveNothing() {
        long countBefore = impl.count(new Company());
        impl.remove(new Company());
        long countAfter = impl.count(new Company());
        assertEquals(countBefore, countAfter);
    }

    /**
     * Test that when remove is called on a null entity, nothing happens, and no exception is thrown
     */
    @Test
    public void testRemoveNull() {
        impl.remove(null);
    }

    /**
     * Test the count method, that it correctly increments and decrements
     */
    @Test
    public void testCount() {
        long initialCount = impl.count(new Company());
        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        impl.insert(company);

        long countAfterInsert = impl.count(new Company());
        assertEquals(initialCount + 1, countAfterInsert);

        impl.remove(company);

        long finalCount = impl.count(new Company());
        assertEquals(initialCount, finalCount);
    }

    /**
     * Test that a count on a null entity returns 0L, and no exception is thrown
     */
    @Test
    public void testCountnull() {
        long count = impl.count(null);
        assertEquals(0L, count);
    }

}

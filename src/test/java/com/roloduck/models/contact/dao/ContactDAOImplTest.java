package com.roloduck.models.contact.dao;

import com.roloduck.IntegrationTest;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.SubscriptionType;
import com.roloduck.models.company.dao.CompanyDAO;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.dao.PartnerDAO;
import org.junit.Test;
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
 * @since 3/25/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@IntegrationTest
@Transactional
public class ContactDAOImplTest {

    @Autowired
    private ContactDAO impl;
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private PartnerDAO partnerDAO;

    @Test
    public void testFindContactsByCompanyId() {
        Contact contact = createContact("Name", "lastname", "emailadresssss");
        insertContact(contact);
        long companyId = contact.getCompanyId();

        List<Contact> contacts = impl.findContactsByCompanyId(companyId);
        boolean contactFound = false;

        for(Contact c: contacts) {
            if(c.getContactFirstName().equals(contact.getContactFirstName()) &&
                    c.getContactLastName().equals(contact.getContactLastName()) &&
                    c.getContactEmail().equals(contact.getContactEmail()) &&
                    c.getPartnerId() == contact.getPartnerId() &&
                    c.getCompanyId() == contact.getCompanyId()) {
                contactFound = true;
            }
        }

        assertTrue(contactFound);
    }

    @Test
    public void testFindContactsByPartnerId() {
        Contact contact = createContact("Name", "lastname", "emailadresssss");
        insertContact(contact);
        long partnerId = contact.getPartnerId();

        List<Contact> contacts = impl.findContactsByPartnerId(partnerId);
        boolean contactFound = false;

        for(Contact c: contacts) {
            if(c.getContactFirstName().equals(contact.getContactFirstName()) &&
                    c.getContactLastName().equals(contact.getContactLastName()) &&
                    c.getContactEmail().equals(contact.getContactEmail()) &&
                    c.getPartnerId() == contact.getPartnerId() &&
                    c.getCompanyId() == contact.getCompanyId()) {
                contactFound = true;
            }
        }

        assertTrue(contactFound);
    }

    /**
     * Tests that findContactsByCompanyId
     */
    @Test
    public void testFindContactsByCompanyIdNotFound() {
        assertEquals(0, impl.findContactsByCompanyId(-999).size());
    }

    private Contact createContact(String firstName, String lastName, String email) {
        Contact contact = new Contact();
        contact.setContactFirstName(firstName);
        contact.setContactLastName(lastName);
        contact.setContactEmail(email);
        return contact;
    }

    private void insertContact(Contact newContact) {
        // Create the company we need to attach to a Partner
        Company company = new Company("This is a test company sjfaksjaksfkjskfsfa",
                SubscriptionType.FREE_SUBSCRIPTION);
        company.setCompanyIdentifyingString(UUID.randomUUID().toString());
        companyDAO.insertCompany(company);

        // Now create the partner we need to attach to the contact
        Partner partner = new Partner();
        partner.setPartnerName("This is a test partnerName kflaufieugiw");
        partner.setCompanyId(company.getId());
        partnerDAO.insertPartner(partner);

        // Now add a Contact
        newContact.setPartnerId(partner.getId());
        newContact.setCompanyId(company.getId());
        impl.insertContact(newContact);
    }

}

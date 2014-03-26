package com.roloduck.models.contact.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.contact.Contact;
import com.roloduck.user.User;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

public interface ContactService {

    /**
     * Create a contact and set the appropriate company Id
     * @param contact the contact to be created
     * @param user the user who created the contact, use this to get the companyId
     */
    public void createContact(Contact contact, User user) throws ServiceLogicException;

    /**
     * Find all contacts that belong to the given company. Make sure the company is *ACTIVE*
     * @param companyId the id of the company
     * @return a list of all contacts belonging to that company
     */
    public List<Contact> findAllCompanyContacts(long companyId) throws ServiceLogicException;
}

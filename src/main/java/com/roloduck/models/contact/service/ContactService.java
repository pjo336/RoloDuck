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
     * @param partnerId the id of the related partner
     */
    public void createContact(Contact contact, User user, long partnerId) throws ServiceLogicException;

    /**
     * Find all contacts that belong to the given company. Make sure the company is *ACTIVE*
     * @param companyId the id of the company
     * @return a list of all contacts belonging to that company
     */
    public List<Contact> findAllCompanyContacts(long companyId) throws ServiceLogicException;

    /**
     * Restore the contact with the given id
     * @param id the id of the contact
     * @return the contact with this id
     * @throws ServiceLogicException Throws if there is no contact with the matching id
     */
    public Contact restoreById(long id) throws ServiceLogicException;

    /**
     * Remove the given contact
     * @param contact the contact to be removed
     */
    public void removeContact(Contact contact);

    /**
     * Update the given contact information
     * @param contact the contact with the updated information, should exist already
     * @throws ServiceLogicException
     */
    public void updateContact(Contact contact) throws ServiceLogicException;
}

package com.roloduck.models.contact.dao;

import com.roloduck.exception.DAOException;
import com.roloduck.models.contact.Contact;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

public interface ContactDAO {

    /**
     * Insert a contact into the database
     * @param contact the contact to insert
     */
    public void insertContact(Contact contact);

    /**
     * Find the contact with the given id. Throws an exception if nothing found
     * @param id the id of the contact
     * @return the contact with that id
     * @throws DAOException
     */
    public Contact restoreById(long id) throws DAOException;

    /**
     * Find a list of all contacts with the given company id
     * @param companyId the id of the company to search on
     * @return a list of contacts
     */
    public List<Contact> findContactsByCompanyId(long companyId);

    /**
     * Remove this contact from the database
     * @param contact the contact to be removed
     */
    public void removeContact(Contact contact);
}

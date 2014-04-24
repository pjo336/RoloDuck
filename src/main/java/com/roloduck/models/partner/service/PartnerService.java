package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.project.Project;
import com.roloduck.user.User;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

public interface PartnerService {

    /**
     * Create a partner and add the partner to the database
     * @param partner the partner to be created
     * @param user the user that created the partner
     */
    public void createPartner(Partner partner, User user) throws ServiceLogicException;

    /**
     * Return the partner with the given id
     * @param partnerId the id of the partner
     * @return the correct partner
     */
    public Partner restoreById(long partnerId) throws ServiceLogicException;

    /**
     * Return a list of all partners in the database for the *ACTIVE* company
     * @param companyId the id of the company
     * @return a list of all partners belonging to this company
     */
    public List<Partner> findAllCompanyPartners(long companyId) throws ServiceLogicException;

    /**
     * Return a list of all partners in the db for the ACTIVE company, sorted by partner name
     * @param companyId the id of the company
     * @return a list of all partners belonging to this company
     * @throws ServiceLogicException
     */
    public List<Partner> findAllCompanyPartnersSortedAlphabetically(long companyId) throws ServiceLogicException;

    /**
     * Find a list of all the partners that belong to the given project
     * @param projectId the id of the project
     * @return a list of partners that belong to the project
     */
    public List<Partner> findAllProjectPartners(long projectId);

    /**
     * Find a list of all the projects that belong to the given partner
     * @param partnerId the partner being searched on
     * @return a list of projects
     */
    public List<Project> findAllConnectedProjects(long partnerId) throws ServiceLogicException;

    /**
     * Find all contacts that the given partner owns
     * @param partnerId the owning partner
     * @return A list of all contacts belonging to the owning partner
     * @throws ServiceLogicException
     */
    List<Contact> findAllAssociatedContacts(long partnerId) throws ServiceLogicException;

    /**
     * Remove the partner with the given id
     * @param partnerId the partner to remove
     */
    void removePartner(long partnerId) throws ServiceLogicException;
}

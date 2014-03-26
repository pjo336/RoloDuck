package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.Partner;
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
     * Assign the given partner to the project with the given id, into the ProjPartAssoc table
     * @param partner The partner to assign
     * @param projectId The project the partner is assigned to
     * @throws ServiceLogicException
     */
    public void assignPartnerToProject(Partner partner, long projectId) throws ServiceLogicException;

    /**
     * Return a list of all partners in the database for the active company
     * @return
     */
    public List<Partner> findAllPartners();

    /**
     * Find a list of all the partners that belong to the given project
     * @param projectId the id of the project
     * @return a list of partners that belong to the project
     */
    public List<Partner> findAllProjectPartners(long projectId);
}

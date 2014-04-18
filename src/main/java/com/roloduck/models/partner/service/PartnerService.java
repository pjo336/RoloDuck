package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
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
     * Assign the given partner to the project with the given id, into the ProjPartAssoc table
     * @param partner The partner to assign
     * @param projectId The project the partner is assigned to
     * @throws ServiceLogicException
     */
    public void assignPartnerToProject(Partner partner, long projectId) throws ServiceLogicException;

    /**
     * Unassign the given partner from the project. If its not assigned to the project already, do nothing.
     * @param partner the partner to unassign
     * @param projectId the id of the project the partner is currently assigned to
     * @throws ServiceLogicException
     */
    public void unassignPartnerFromProject(Partner partner, long projectId) throws ServiceLogicException;

    /**
     * Return a list of all partners in the database for the *ACTIVE* company
     * @param companyId the id of the company
     * @return a list of all partners belonging to this company
     */
    public List<Partner> findAllCompanyPartners(long companyId) throws ServiceLogicException;

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
}

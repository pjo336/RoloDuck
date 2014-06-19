package com.roloduck.models.projpartassoc.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.Partner;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 4/19/14
 * RoloDuck
 */

public interface ProjPartAssocService
{
    /**
     * Assign the given partner to the project with the given id, into the ProjPartAssoc table
     * Ensure both the partner and the project exist, and check if there is already an association existing
     * between both. Throw an exception if there is for a unique constraint violation.
     * @param partnerId The partner to assign
     * @param projectId The project the partner is assigned to
     * @throws ServiceLogicException
     */
    public void assignPartnerToProject(long partnerId, long projectId) throws ServiceLogicException;

    /**
     * Unassign the given partner from the project. If its not assigned to the project already, do nothing.
     * @param partnerId the partner to unassign
     * @param projectId the id of the project the partner is currently assigned to
     * @throws ServiceLogicException
     */
    public void unassignPartnerFromProject(long partnerId, long projectId) throws ServiceLogicException;

    List<Partner> findAssociatedPartnersToProject(long projectId) throws ServiceLogicException;
}

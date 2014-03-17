package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.model.Partner;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

public interface PartnerService {

    /**
     * Create a partner
     * @param partner the partner to be created
     */
    public void createPartner(Partner partner) throws ServiceLogicException;

    /**
     * Find a list of all the partners that belong to the given project
     * @param projectId the id of the project
     * @return a list of partners that belong to the project
     */
    public List<Partner> findAllProjectPartners(long projectId);
}

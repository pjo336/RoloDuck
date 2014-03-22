package com.roloduck.models.partner.dao;

import com.roloduck.exception.DAOException;
import com.roloduck.models.partner.Partner;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

public interface PartnerDAO {

    /**
     * Insert a partner into the database
     * @param partner the partner to be added to the database
     */
    public void insertPartner(Partner partner);

    /**
     * Inserts a partner and its associated project to the ProjectPartnerAssoc table
     * @param partner the partner to be added to the assoc
     * @param projectId the id of the project to be added to the assoc
     */
    public void insertPartnerProjectAssoc(Partner partner, long projectId);

    /**
     * Find the partner with the given id. If none exists, throw an exception.
     * @param id the id of the partner to be found
     * @return the partner with the given id
     * @exception com.roloduck.exception.DAOException
     */
    public Partner restoreById(long id) throws DAOException;

    /**
     * Find the partners in the assoc table  with the given project id
     * @param projectId the id of the project
     * @return the list of partners associated to this project id
     */
    public List<Partner> findPartnersByProjectId(long projectId);

}

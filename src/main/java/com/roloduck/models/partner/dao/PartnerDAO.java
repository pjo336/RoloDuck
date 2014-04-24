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
     * Find the partner with the given id. If none exists, throw an exception.
     * @param id the id of the partner to be found
     * @return the partner with the given id
     * @exception com.roloduck.exception.DAOException
     */
    public Partner restoreById(long id) throws DAOException;

    /**
     * Find all the partners belonging to the given company
     * @param companyId the id of the company
     * @param toSort should this list be sorted alphabetically
     * @return a list of all partners belonging to the given company
     */
    public List<Partner> findPartnersByCompanyId(long companyId, boolean toSort);

    /**
     * If the given partner exists, remove it from the database, else do nothing
     * @param partner the partner to remove
     */
    public void removePartner(Partner partner);
}

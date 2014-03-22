package com.roloduck.models.partner.dao;

import com.roloduck.exception.DAOException;
import com.roloduck.models.partner.Partner;

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

}

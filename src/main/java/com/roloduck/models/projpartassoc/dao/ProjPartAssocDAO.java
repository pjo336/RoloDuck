package com.roloduck.models.projpartassoc.dao;

import com.roloduck.models.partner.Partner;
import com.roloduck.models.projpartassoc.ProjPartAssoc;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/22/14
 * RoloDuck
 */

public interface ProjPartAssocDAO {

    /**
     * Inserts a partner and its associated project to the ProjPartAssoc table
     * @param assoc the association object to be added, comprised of Partner Id and Project Id
     */
    public void insertAssoc(ProjPartAssoc assoc);

    /**
     * Find the partners in the assoc table  with the given project id
     * @param projectId the id of the project
     * @return the list of partners associated to this project id
     */
    public List<Partner> findPartnersByProjectId(long projectId);
}

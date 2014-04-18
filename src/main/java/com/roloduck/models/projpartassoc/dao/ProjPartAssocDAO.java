package com.roloduck.models.projpartassoc.dao;

import com.roloduck.exception.DAOException;
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
     * Find the partners in the assoc table with the given project id
     * @param projectId the id of the project
     * @return the list of partner IDs associated to this project id
     */
    public List<Long> findPartnersByProjectId(long projectId) throws DAOException;

    /**
     * Find the projects in the assoc table with the given partner id
     * @param partnerId the partner to be searched on
     * @return a list of project ids associated to this project id
     * @throws DAOException
     */
    public List<Long> findProjectsByPartnerId(long partnerId) throws DAOException;

    /**
     * Remove the given association from the database
     * @param assoc the association to be removed
     */
    public void removeAssoc(ProjPartAssoc assoc);

    /**
     * Returns a count of all elements in the table
     * @return the int count of all elements
     */
    public long count();
}

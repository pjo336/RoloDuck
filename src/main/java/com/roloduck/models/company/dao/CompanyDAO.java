package com.roloduck.models.company.dao;

import com.roloduck.exception.NotFoundException;
import com.roloduck.models.company.model.Company;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public interface CompanyDAO {

    /**
     * Insert a new company into the database
     * @param company the company to insert
     */
    public void insertCompany(Company company);

    /**
     * Find the company with the given id. If none exists, throw an exception.
     * @param id the id of the company to be found
     * @return the company with the given id
     * @exception com.roloduck.exception.NotFoundException
     */
    public Company restoreById(long id) throws NotFoundException;

    /**
     * Find the company with the given name. If none exists, throw an exception.
     * @param name the name of the company to be found
     * @return the company with the given name
     * @throws com.roloduck.exception.NotFoundException
     */
    public Company restoreCompanyByName(String name) throws NotFoundException;

    /**
     * Find the company that has the given identifier
     * @param identifier the identifying string of the company
     */
    public Company restoreCompanyByIdentifier(String identifier) throws NotFoundException;
}

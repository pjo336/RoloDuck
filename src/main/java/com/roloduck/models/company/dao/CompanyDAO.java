package com.roloduck.models.company.dao;

import com.roloduck.exception.DAOException;
import com.roloduck.models.company.Company;

import java.util.List;

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
     * @throws com.roloduck.exception.DAOException
     */
    public Company restoreById(long id) throws DAOException;

    /**
     * Find the company with the given name. If none exists, throw an exception.
     * @param name the name of the company to be found
     * @return the company with the given name
     * @throws com.roloduck.exception.DAOException
     */
    public Company restoreCompanyByName(String name) throws DAOException;

    /**
     * Find the company that has the given identifier
     * @param identifier the identifying string of the company
     * @return the compan with the given identifier
     * @throws com.roloduck.exception.DAOException
     */
    public Company restoreCompanyByIdentifier(String identifier) throws DAOException;

    /**
     * Find all companies listed in the database
     * @return the list of companies
     */
    public List<Company> find();
}

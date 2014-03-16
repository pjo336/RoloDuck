package com.roloduck.models.company.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.model.Company;
import com.roloduck.user.model.User;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public interface CompanyService {

    /**
     * Create a company and insert it into the database. This will be the base binding for all users
     * who are a part of this company.
     * Note: Ensure that the company name is unique and create a company identifier uuid before inserting.
     * This uuid will be used to send to other users to signup to this company
     * @param company the company to create
     * @exception com.roloduck.exception.ServiceLogicException
     */
    public void createCompany(Company company) throws ServiceLogicException;

    /**
     * Find the company with the given id
     * @param id the id of the company to be found
     * @throws com.roloduck.exception.ServiceLogicException
     */
    public Company restoreCompanyById(long id) throws ServiceLogicException;

    /**
     * Find the company that the given User belongs to
     * @param user A user who is linked to the company we are searching for
     * @return The company this user belongs to
     * @throws com.roloduck.exception.ServiceLogicException
     */
    public Company restoreCompanyByUser(User user) throws ServiceLogicException;
}

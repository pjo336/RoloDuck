package com.roloduck.models.company.service;

import com.roloduck.exception.BusinessLogicException;
import com.roloduck.models.company.model.Company;

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
     * @exception com.roloduck.exception.BusinessLogicException
     */
    public void createCompany(Company company) throws BusinessLogicException;
}

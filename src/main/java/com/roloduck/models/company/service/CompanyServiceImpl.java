package com.roloduck.models.company.service;

import com.roloduck.exception.BusinessLogicException;
import com.roloduck.exception.NotFoundException;
import com.roloduck.models.company.dao.CompanyDAO;
import com.roloduck.models.company.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDAO companyDAO;

    @Override
    public void createCompany(Company company) throws BusinessLogicException {
        if(company != null) {
            try {
                companyDAO.restoreCompanyByName(company.getCompanyName());
                throw new BusinessLogicException("Company name: " + company.getCompanyName() + " is in use");
            } catch(NotFoundException nfe) {
                // No company with this name exists, so we can create it
                company.setCompanyIdentifyingString(UUID.randomUUID().toString());
                companyDAO.insertCompany(company);
            }
        }
    }
}

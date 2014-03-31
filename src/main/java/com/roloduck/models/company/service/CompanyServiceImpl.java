package com.roloduck.models.company.service;

import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.dao.CompanyDAO;
import com.roloduck.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyDAO companyDAO;

    @Override
    public void createCompany(Company company) throws ServiceLogicException {
        if(company != null) {
            try {
                companyDAO.restoreCompanyByName(company.getCompanyName());
                logger.warn("Attempt to create company: " + company.getId() + " failed. Name: " +
                        company.getCompanyName() + " is already in use.");
                throw new ServiceLogicException("Company name: " + company.getCompanyName() + " is in use.");
            } catch(DAOException de) {
                // Ensure the exception is related to no company with the name given found
                if(de.getMessage().contains("not found") && de.getCause() instanceof EmptyResultDataAccessException) {
                    // No company with this name exists, so we can create it
                    company.setCompanyIdentifyingString(UUID.randomUUID().toString());
                    company.validate();
                    companyDAO.insertCompany(company);
                } else {
                    // The exception was not because no company was found, log and throw the error
                    logger.warn("Attempt to create company ID: " + company.getId() + " NAME: " + company
                            .getCompanyName() + " failed.");
                    throw new ServiceLogicException("There was an error creating your company. Please try again.",
                            de);
                }
            }
        } else {
            logger.warn("The company marked to be created was null.");
            throw new ServiceLogicException("Company Creation failed due to null company.");
        }
    }

    @Override
    public Company restoreCompanyById(long id) throws ServiceLogicException {
        try {
            return companyDAO.restoreById(id);
        } catch(DAOException de) {
            logger.warn(de.getMessage());
            throw new ServiceLogicException(de);
        }
    }

    @Override
    public Company restoreCompanyByUser(User user) throws ServiceLogicException {
        try {
            if(user != null) {
                return companyDAO.restoreById(user.getCompanyId());
            } else {
                logger.warn("User given to restoreCompanyByUser was null.");
                return null;
            }
        } catch(DAOException de) {
            logger.warn(de.getMessage());
            throw new ServiceLogicException(de);
        }
    }

    @Override
    public Company restoreCompanyByName(String name) throws ServiceLogicException {
        try {
            return companyDAO.restoreCompanyByName(name);
        } catch(DAOException de) {
            logger.warn(de.getMessage());
            throw new ServiceLogicException(de);
        }
    }

}

package com.roloduck.models.company.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.NotFoundException;
import com.roloduck.models.company.model.Company;
import com.roloduck.models.company.model.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

@Repository
public class CompanyDAOImpl extends RoloDuckEntityDAOImpl<Company> implements CompanyDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final String TABLE_NAME = "RD_COMPANY";

    @Override
    public void insertCompany(Company company) {
        super.insert(company);
    }

    @Override
    public Company restoreById(long id) throws NotFoundException {
        return super.restoreById(id, new Company());
    }

    @Override
    public Company restoreCompanyByName(String name) throws NotFoundException {
        final String SQL = "SELECT * FROM " + TABLE_NAME + " where company_name = ?";
        try {
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{name}, new CompanyMapper());
        } catch(IncorrectResultSizeDataAccessException e) {
            throw new NotFoundException("Company " + name + " was not found.");
        }
    }

    @Override
    public Company restoreCompanyByIdentifier(String identifier) throws NotFoundException {
        final String SQL = "SELECT * FROM " + TABLE_NAME + " where company_identifying_string = ?";
        try {
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{identifier}, new CompanyMapper());
        } catch(IncorrectResultSizeDataAccessException e) {
            throw new NotFoundException("Company " + identifier + " was not found.");
        }
    }
}

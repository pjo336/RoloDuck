package com.roloduck.models.company.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.CompanyMapper;
import com.roloduck.utils.SQLUtils;
import com.roloduck.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Company restoreById(long id) throws DAOException {
        return super.restoreById(id, new Company());
    }

    @Override
    public Company restoreCompanyByName(String name) throws DAOException {
        if(name == null) {
            throw new DAOException("Restore by company name was called with a null name.");
        }
        Company company = new Company();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(company.getAllColumnNames()) + " FROM " +
                TABLE_NAME + " where company_name = ?";
        SQLUtils.printSQL(SQL);
        try {
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{name}, new CompanyMapper());
        } catch(EmptyResultDataAccessException e) {
            throw new DAOException("Company " + name + " was not found.", e);
        }
    }

    @Override
    public Company restoreCompanyByIdentifier(String identifier) throws DAOException {
        if(identifier == null) {
            throw new DAOException("Restore by company identifier was called with a null identifier.");
        }
        Company company = new Company();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(company.getAllColumnNames()) + " FROM " +
                TABLE_NAME + " where company_identifying_string = ?";
        SQLUtils.printSQL(SQL);
        try {
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{identifier}, new CompanyMapper());
        } catch(EmptyResultDataAccessException e) {
            throw new DAOException("Company " + identifier + " was not found.", e);
        }
    }

    @Override
    public List<Company> find() {
        return super.find(new Company());
    }

    @Override
    public long count() {
        return super.count(new Company());
    }
}

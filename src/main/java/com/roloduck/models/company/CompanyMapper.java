package com.roloduck.models.company;

import com.roloduck.exception.NotFoundException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public class CompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong("id"));
        company.setCompanyName(resultSet.getString("company_name"));
        try {
            company.setSubscriptionType(resultSet.getInt("subscription_type"));
        } catch (NotFoundException nfe) {
            throw new SQLException(nfe.getMessage());
        }
        company.setCompanyIdentifyingString(resultSet.getString("company_identifying_string"));
        return company;
    }
}

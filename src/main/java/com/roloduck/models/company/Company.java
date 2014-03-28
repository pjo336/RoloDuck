package com.roloduck.models.company;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.NotFoundException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public class Company implements RoloDuckEntity {

    private static final String TABLE_NAME = "RD_COMPANY";

    private long id;
    private String companyName;
    private SubscriptionType subscriptionType;
    private String companyIdentifyingString;

    public Company() {}

    public Company(String companyName, SubscriptionType subType) {
        this.companyName = companyName;
        this.subscriptionType = subType;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getDistinctColumnNames() {
        return new String[]{"company_name", "subscription_type", "company_identifying_string"};
    }

    @Override
    public String[] getAllColumnNames() {
        return new String[]{"id", "company_name", "subscription_type", "company_identifying_string", "date_created",
                "date_modified"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final Company newCompany = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setString(1, newCompany.getCompanyName());
                ps.setInt(2, newCompany.getSubscriptionType().value());
                ps.setString(3, newCompany.getCompanyIdentifyingString());
                return ps;
            }
        };
    }

    @Override
    public RowMapper getEntityMapper() {
        return new CompanyMapper();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    /**
     * Setter using an int value
     * @param subscriptionType the int value of subscription type
     */
    public void setSubscriptionType(int subscriptionType) throws NotFoundException {
        this.subscriptionType = SubscriptionType.getTypeByValue(subscriptionType);
    }

    public String getCompanyIdentifyingString() {
        return companyIdentifyingString;
    }

    public void setCompanyIdentifyingString(String companyIdentifyingString) {
        this.companyIdentifyingString = companyIdentifyingString;
    }
}

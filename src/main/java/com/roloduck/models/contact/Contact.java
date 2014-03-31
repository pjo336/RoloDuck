package com.roloduck.models.contact;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.ServiceLogicException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/8/14
 * RoloDuck
 */

public class Contact implements RoloDuckEntity {

    private static final String TABLE_NAME = "RD_CONTACT";

    private long id;
    private String contactFirstName;
    private String contactLastName;
    private String contactTitle;
    private String contactEmail;
    private String contactPhone;
    private long partnerId;
    private long companyId;

    public Contact(){}

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getDistinctColumnNames() {
        return new String[]{"contact_first_name", "contact_last_name", "contact_title",
                "contact_email", "contact_phone", "partner_id", "company_id"};
    }

    @Override
    public String[] getAllColumnNames() {
        return new String[]{"id", "contact_first_name", "contact_last_name", "contact_title",
                "contact_email", "contact_phone", "partner_id", "company_id", "date_created",
                "date_modified"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final Contact newContact = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setString(1, newContact.getContactFirstName());
                ps.setString(2, newContact.getContactLastName());
                ps.setString(3, newContact.getContactTitle());
                ps.setString(4, newContact.getContactEmail());
                ps.setString(5, newContact.getContactPhone());
                ps.setLong(6, newContact.getPartnerId());
                ps.setLong(7, newContact.getCompanyId());
                return ps;
            }
        };
    }

    @Override
    public RowMapper getEntityMapper() {
        return new ContactMapper();
    }

    @Override
    public void validate() throws ServiceLogicException {
        StringBuilder errors = new StringBuilder();
        if(getContactFirstName() == null || getContactFirstName().equalsIgnoreCase("")) {
            errors.append("Contact needs a valid First Name.\n");
        }
        if(getContactLastName() == null || getContactLastName().equalsIgnoreCase("")) {
            errors.append("Contact needs a valid Last Name.\n");
        }
        // TODO validate email string?
        if(getContactEmail() == null || getContactEmail().equalsIgnoreCase("")) {
            errors.append("Contact needs a valid Email Address.\n");
        }
        // TODO contact doesnt need company ID?
        if(getCompanyId() < 1) {
            errors.append("Contact needs a valid Company attached.\n");
        }
        if(getPartnerId() < 1) {
            errors.append("Contact needs a valid Partner attached.\n");
        }
        if(errors.length() > 0) {
            throw new ServiceLogicException(errors.toString());
        }
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    public long getCompanyId() { return companyId; }

    public void setCompanyId(long companyId) { this.companyId = companyId; }
}

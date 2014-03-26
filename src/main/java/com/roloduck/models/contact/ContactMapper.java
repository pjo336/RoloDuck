package com.roloduck.models.contact;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/8/14
 * RoloDuck
 */

public class ContactMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet resultSet, int i) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong("id"));
        contact.setContactFirstName(resultSet.getString("contact_first_name"));
        contact.setContactLastName(resultSet.getString("contact_last_name"));
        contact.setContactTitle(resultSet.getString("contact_title"));
        contact.setContactEmail(resultSet.getString("contact_email"));
        contact.setContactPhone(resultSet.getString("contact_phone"));
        contact.setPartnerId(resultSet.getLong("partner_id"));
        contact.setCompanyId(resultSet.getLong("company_id"));
        return contact;
    }
}

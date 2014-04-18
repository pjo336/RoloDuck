package com.roloduck.models.partner;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.project.Project;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/8/14
 * RoloDuck
 */

public class Partner implements RoloDuckEntity {

    private static final String TABLE_NAME = "RD_PARTNER";

    private long id;
    private String partnerName;
    private String partnerDescription;
    private long companyId;

    private List<Project> associatedProjects;
    private List<Contact> associatedContacts;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getDistinctColumnNames() {
        return new String[]{"partner_name", "partner_description", "company_id"};
    }

    @Override
    public String[] getAllColumnNames() {
        return new String[]{"id", "partner_name", "partner_description", "company_id", "date_created",
                "date_modified"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final Partner partner = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setString(1, partner.getPartnerName());
                ps.setString(2, partner.getPartnerDescription());
                ps.setLong(3, partner.getCompanyId());
                return ps;
            }
        };
    }

    @Override
    public RowMapper getEntityMapper() {
        return new PartnerMapper();
    }

    @Override
    public void validate() throws ServiceLogicException {
        StringBuilder errors = new StringBuilder();
        if(getPartnerName() == null || getPartnerName().equalsIgnoreCase("")) {
            errors.append("Please enter a Partner Name.\n");
        }
        if(getCompanyId() < 1) {
            errors.append("Partner must have a valid Company attached.\n");
        }
        if(errors.length() > 0) {
            throw new ServiceLogicException(errors.toString());
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerDescription() {
        return partnerDescription;
    }

    public void setPartnerDescription(String partnerDescription) {
        this.partnerDescription = partnerDescription;
    }

    public long getCompanyId() { return companyId; }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public List<Project> getAssociatedProjects() {
        return associatedProjects;
    }

    public void setAssociatedProjects(List<Project> associatedProjects) {
        this.associatedProjects = associatedProjects;
    }

    public List<Contact> getAssociatedContacts() {
        return associatedContacts;
    }

    public void setAssociatedContacts(List<Contact> associatedContacts) {
        this.associatedContacts = associatedContacts;
    }
}

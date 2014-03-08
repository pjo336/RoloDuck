package com.roloduck.models.partner.model;

import com.roloduck.entity.RoloDuckEntity;
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

public class Partner implements RoloDuckEntity {

    private static final String TABLE_NAME = "RD_PARTNER";

    private long id;
    private String partnerName;
    private String partnerDescription;
    private long projectId;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"partner_name", "partner_description", "project_id"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final Partner partner = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setString(1, partner.getPartnerName());
                ps.setString(2, partner.getPartnerDescription());
                ps.setLong(3, partner.getProjectId());
                return ps;
            }
        };
    }

    @Override
    public RowMapper getEntityMapper() {
        return new PartnerMapper();
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

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}

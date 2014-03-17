package com.roloduck.models.project.model;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.models.partner.model.Partner;
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

public class Project implements RoloDuckEntity{

    private static final String TABLE_NAME = "RD_PROJECT";

    private long id;
    private String projectName;
    private String projectDescription;
    private long createdByUser;
    private long companyId;

    private List<Partner> partnerAssocs;

    public Project(){}

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"project_name", "project_description", "created_by_user", "company_id"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final Project newContact = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setString(1, newContact.getProjectName());
                ps.setString(2, newContact.getProjectDescription());
                ps.setLong(3, newContact.getCreatedByUser());
                ps.setLong(4, newContact.getCompanyId());
                return ps;
            }
        };
    }

    @Override
    public RowMapper getEntityMapper() {
        return new ProjectMapper();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public long getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(long createdByUser) {
        this.createdByUser = createdByUser;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public List<Partner> getPartnerAssocs() {
        return partnerAssocs;
    }

    public void setPartnerAssocs(List<Partner> partnerAssocs) {
        this.partnerAssocs = partnerAssocs;
    }
}

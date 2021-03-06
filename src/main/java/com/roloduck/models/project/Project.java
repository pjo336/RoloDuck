package com.roloduck.models.project;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.Partner;
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

    public Project(String name, String desc, long userId, long compId) {
        this.projectName = name;
        this.projectDescription = desc;
        this.createdByUser = userId;
        this.companyId = compId;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getDistinctColumnNames() {
        return new String[]{"project_name", "project_description", "created_by_user", "company_id"};
    }

    @Override
    public String[] getAllColumnNames() {
        return new String[]{"id", "project_name", "project_description", "created_by_user", "company_id",
                "date_created", "date_modified"};
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

    @Override
    public void validate() throws ServiceLogicException {
        StringBuilder errors = new StringBuilder();
        if(getProjectName() == null || getProjectName().equalsIgnoreCase("")) {
            errors.append("Project needs a valid Project Name.\n");
        }
        if(getCreatedByUser() < 1) {
            errors.append("Project must have a valid User attached.\n");
        }
        if(getCompanyId() < 1) {
            errors.append("Project must have a valid Company attached.\n");
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

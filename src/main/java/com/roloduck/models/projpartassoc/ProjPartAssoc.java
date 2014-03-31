package com.roloduck.models.projpartassoc;

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
 * @since 3/22/14
 * RoloDuck
 */

public class ProjPartAssoc implements RoloDuckEntity {

    private static final String TABLE_NAME = "RD_PROJECT_PARTNER_ASSOC";
    private long id;
    private long projectId;
    private long partnerId;

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getDistinctColumnNames() {
        return new String[]{"project_id", "partner_id"};
    }

    @Override
    public String[] getAllColumnNames() {
        return new String[]{"id", "project_id", "partner_id", "date_created", "date_modified"};
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator(final String SQL) {
        final ProjPartAssoc assoc = this;
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setLong(1, assoc.getProjectId());
                ps.setLong(2, assoc.getPartnerId());
                return ps;
            }
        };
    }

    @Override
    public RowMapper getEntityMapper() {
        return new ProjPartAssocMapper();
    }

    @Override
    public void validate() throws ServiceLogicException {
        StringBuilder errors = new StringBuilder();
        if(getProjectId() < 1) {
            errors.append("Project Partner Association must have a valid Project attached.\n");
        }
        if(getPartnerId() < 1) {
            errors.append("Project Partner Association must have a valid Partner attached.\n");
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

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }
}

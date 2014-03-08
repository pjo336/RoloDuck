package com.roloduck.models.project.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/8/14
 * RoloDuck
 */

public class ProjectMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet resultSet, int i) throws SQLException {
        Project contact = new Project();
        contact.setId(resultSet.getLong("id"));
        contact.setProjectName(resultSet.getString("project_name"));
        contact.setProjectDescription(resultSet.getString("partner_description"));
        contact.setCreatedByUser(resultSet.getLong("created_by_user"));
        contact.setCompanyId(resultSet.getLong("company_id"));
        return contact;
    }
}

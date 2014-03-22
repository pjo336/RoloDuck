package com.roloduck.models.project;

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
        Project project = new Project();
        project.setId(resultSet.getLong("id"));
        project.setProjectName(resultSet.getString("project_name"));
        project.setProjectDescription(resultSet.getString("project_description"));
        project.setCreatedByUser(resultSet.getLong("created_by_user"));
        project.setCompanyId(resultSet.getLong("company_id"));
        return project;
    }
}

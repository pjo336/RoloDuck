package com.roloduck.models.project.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.NotFoundException;
import com.roloduck.models.project.model.Project;
import com.roloduck.models.project.model.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

@Repository
public class ProjectDAOImpl extends RoloDuckEntityDAOImpl<Project> implements ProjectDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final String TABLE_NAME = "RD_PROJECT";

    @Override
    public void insertProject(Project project) {
        super.insert(project);
    }

    @Override
    public Project restoreById(long id) throws NotFoundException {
        return super.findById(id, new Project());
    }

    @Override
    public Project findProjectByName(String name) {
        try {
            final String SQL = "SELECT * FROM " + TABLE_NAME + " where  project_name = ?";
            return jdbcTemplateObject.queryForObject(SQL,
                    new Object[]{name}, new ProjectMapper());
        } catch(EmptyResultDataAccessException emptyException) {
            // We don't want an exception for getting no result here, so just return null
            return null;
        }
    }

    @Override
    public void updateOrStoreProject(Project project) {

    }

    @Override
    public void removeProject(Project project) {
        super.remove(project);
    }
}

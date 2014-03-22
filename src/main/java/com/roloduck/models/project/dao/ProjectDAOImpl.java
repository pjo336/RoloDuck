package com.roloduck.models.project.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Project restoreById(long id) throws DAOException {
        return super.restoreById(id, new Project());
    }

    @Override
    public List<Project> findProjectsByName(String name) {
        final String SQL = "SELECT * FROM " + TABLE_NAME + " where project_name = ?";
        return jdbcTemplateObject.query(SQL, new Object[]{name}, new ProjectMapper());
    }

    @Override
    public List<Project> findProjectsByCompanyId(long companyId) {
        final String SQL = "SELECT * FROM " + TABLE_NAME + " where company_id = ?";
        return jdbcTemplateObject.query(SQL, new Object[]{companyId}, new ProjectMapper());
    }

    @Override
    public void updateOrStoreProject(Project project) {
        // TODO
    }

    @Override
    public void removeProject(Project project) {
        super.remove(project);
    }
}

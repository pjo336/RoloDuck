package com.roloduck.models.project.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.ProjectMapper;
import com.roloduck.utils.SQLUtils;
import com.roloduck.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public List<Project> findProjectsByName(String name) throws DAOException {
        Project project = new Project();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(project.getAllColumnNames()) + " FROM " +
                TABLE_NAME + " where project_name = ?";
        SQLUtils.printSQL(SQL);
        try {
            return jdbcTemplateObject.query(SQL, new Object[]{name}, new ProjectMapper());
        } catch (DataAccessException dae) {
            throw new DAOException("There was an access exception while finding the project by name.");
        }
    }

    @Override
    public List<Project> findProjectsByCompanyId(long companyId, boolean toSort) throws DAOException {
        Project project = new Project();
        String query = "SELECT " + StringUtils.convertStrArrToSQLColStr(project.getAllColumnNames()) + " FROM " +
                TABLE_NAME + " where company_id = ?";
        if(toSort) {
            query += " ORDER BY project_name";
        }
        final String SQL  = query;
        SQLUtils.printSQL(SQL);
        try {
            return jdbcTemplateObject.query(SQL, new Object[]{companyId}, new ProjectMapper());
        } catch (DataAccessException dae) {
            throw new DAOException("There was an access exception while finding the project by Company id.");
        }
    }

    @Override
    public void updateProject(Project project) throws DAOException {
        final String SQL = "UPDATE " + TABLE_NAME + " SET project_name = ?, project_description = ? where id = ?";
        SQLUtils.printSQL(SQL);
        try {
            jdbcTemplateObject.update(SQL, project.getProjectName(), project.getProjectDescription(),
                    project.getId());
        } catch(DataAccessException dae) {
            throw new DAOException("There was a data access exception while updating a project.");
        }
    }

    @Override
    public void removeProject(Project project) {
        super.remove(project);
    }

    @Override
    public long count() {
        return super.count(new Project());
    }
}

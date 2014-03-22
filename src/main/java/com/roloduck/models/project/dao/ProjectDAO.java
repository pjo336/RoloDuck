package com.roloduck.models.project.dao;

import com.roloduck.exception.DAOException;
import com.roloduck.models.project.Project;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

public interface ProjectDAO {

    /**
     * Insert a project into the database
     * @param project the project to be added to the database
     */
    public void insertProject(Project project);

    /**
     * Find the project with the given id. If none exists, throw an exception.
     * @param id the id of the project to be found
     * @return the project with the given id
     * @exception com.roloduck.exception.DAOException
     */
    public Project restoreById(long id) throws DAOException;

    /**
     * Find the projects with the given name.
     * @param name the name of the projects to be found
     * @return the list of projects with the given name
     */
    public List<Project> findProjectsByName(String name);

    /**
     * Find the projects with the given company id
     * @param companyId the id of the company
     * @return the list of projects with this company id
     */
    public List<Project> findProjectsByCompanyId(long companyId);

    /**
     * If the project exists, update the record to reflect any changes,
     * otherwise insert the project
     * @param project the project to be updated
     */
    public void updateOrStoreProject(Project project);

    /**
     * If the project exists, delete the project, otherwise nothing happens.
     * @param project the project to be removed from the database
     */
    public void removeProject(Project project);
}

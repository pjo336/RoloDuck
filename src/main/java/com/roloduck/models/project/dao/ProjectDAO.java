package com.roloduck.models.project.dao;

import com.roloduck.exception.NotFoundException;
import com.roloduck.models.project.model.Project;

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
     * @exception com.roloduck.exception.NotFoundException
     */
    public Project restoreById(long id) throws NotFoundException;

    /**
     * Find the project with the given name.
     * @param name the name of the project to be found
     * @return the project with the given name
     * @throws com.roloduck.exception.NotFoundException
     */
    public Project findProjectByName(String name) throws NotFoundException;

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

package com.roloduck.models.project.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.project.Project;
import com.roloduck.user.User;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/13/14
 * RoloDuck
 */

public interface ProjectService {

    /**
     * Create a project and assign it to a company. Mark the user that created it. Make sure both the company
     * and the user exist.
     * @param project the project to be created
     * @param createdByUser the user who is creating this project. Pull the company the project will belong to
     *                      from this user
     */
    public void createProject(Project project, User createdByUser) throws ServiceLogicException;

    /**
     * Find a list of all the projects that belong to the given company
     * @param companyId the id of the company
     * @return a list of projects that belong to the company
     */
    public List<Project> findAllCompanyProjects(long companyId) throws ServiceLogicException;

    /**
     * Find the project with the given Id
     * @param projectId the id to search for
     * @return the project with this id
     * @throws ServiceLogicException
     */
    public Project restoreProjectById(long projectId) throws ServiceLogicException;
}

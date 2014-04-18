package com.roloduck.models.project.service;

import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.dao.ProjectDAO;
import com.roloduck.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    public void createProject(Project project, User createdByUser) throws ServiceLogicException {
        if(project != null) {
            // Make sure the user is relevant
            if(createdByUser != null) {
                project.setCreatedByUser(createdByUser.getId());
                project.setCompanyId(createdByUser.getCompanyId());
                project.validate();
                projectDAO.insertProject(project);
            }
        }
    }

    @Override
    public List<Project> findAllCompanyProjects(long companyId) throws ServiceLogicException {
        // TODO Make sure company is active
        // Maybe use company instead of company ID?
        try {
            return projectDAO.findProjectsByCompanyId(companyId);
        } catch(DAOException de) {
            throw new ServiceLogicException("There was a problem finding all Projects of this Company.");
        }
    }

    @Override
    public Project restoreProjectById(long projectId) throws ServiceLogicException {
        try {
            return projectDAO.restoreById(projectId);
        } catch (DAOException de) {
            throw new ServiceLogicException("The project with id: " + projectId + " could not be found.");
        }
    }
}

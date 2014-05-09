package com.roloduck.models.project.dao;

import com.roloduck.exception.DAOException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.SubscriptionType;
import com.roloduck.models.company.dao.CompanyDAO;
import com.roloduck.models.project.Project;
import com.roloduck.user.User;
import com.roloduck.user.dao.UserDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@Transactional
public class ProjectDAOImplTest {

    @Autowired
    ProjectDAO impl;
    @Autowired
    CompanyDAO companyDAO;
    @Autowired
    UserDAO userDAO;

    @Test
    public void testFindProjectsByName() throws DAOException {
        String name = "Fake Project";
        // Inser multiple projects with the same name
        int projectSize = 3;
        for(int i = 0; i < projectSize; i++) {
            impl.insertProject(getFakeProject(name));
        }
        Assert.assertEquals("The wrong amount of projects were present", projectSize,
                impl.findProjectsByName(name).size());
    }

    @Test
    public void testFindProjectsByNameNotFound() throws DAOException {
        impl.findProjectsByName(UUID.randomUUID().toString().substring(20));
    }

    @Test
    public void testFindProjectByCompanyIdSorted() throws DAOException {
        String name1 = "bMy fake project using for testing";
        impl.insertProject(getFakeProject(name1));
        impl.insertProject(getFakeProject("zMy fake project using for testing2"));
        impl.insertProject(getFakeProject("aMy fake project using for testing3"));
        Project project = impl.findProjectsByName(name1).get(0);
        long companyId = project.getCompanyId();
        // Now test finding and if the sorting worked
        // First find without a sort
        List<Project> projectsNotSorted = impl.findProjectsByCompanyId(companyId, false);
        // Note this could not always be false, so was removed
        //assertFalse(checkIfListOfProjectsIsSortedByName(projectsNotSorted));
        // Now find a list that is sorted
        List<Project> projectsSorted = impl.findProjectsByCompanyId(companyId, true);
        assertTrue(checkIfListOfProjectsIsSortedByName(projectsSorted));
    }
    @Test
    public void testFindProjectsByCompanyIdNotFound() throws DAOException {
        impl.findProjectsByCompanyId(-999, false);
    }

    /**
     * Quickly create a project to be used in testing
     * @param name the name given to the project
     * @return the project
     */
    private Project getFakeProject(String name) {
        Project project = new Project();
        project.setProjectName(name);
        long companyId = getAttachedCompany().getId();
        project.setCompanyId(companyId);
        long userId = getAttachedUser(companyId).getId();
        project.setCreatedByUser(userId);
        return project;
    }

    /**
     * Return a company that exists in the database for testing
     * @return the company that exists
     */
    private Company getAttachedCompany() {
        List<Company> companies = companyDAO.find();
        if(companies.size() > 0) {
            return companies.get(0);
        } else {
            Company newCompany = new Company(UUID.randomUUID().toString(), SubscriptionType.FREE_SUBSCRIPTION);
            companyDAO.insertCompany(newCompany);
            return newCompany;
        }
    }

    private User getAttachedUser(long companyId) {
        List<User> users = userDAO.find();
        if(users.size() > 0) {
            return users.get(0);
        } else {
            User newUser = new User();
            newUser.setCompanyId(companyId);
            userDAO.insertUser(newUser);
            return newUser;
        }
    }

    private boolean checkIfListOfProjectsIsSortedByName(List<Project> listToCheckSort) {
        for(int i = 0; i < listToCheckSort.size(); i++) {
            if(i + 1 < listToCheckSort.size()) {
                String projName = listToCheckSort.get(i).getProjectName();
                String projName2 = listToCheckSort.get(i + 1).getProjectName();
                int compare = projName.compareToIgnoreCase(projName2);
                if(compare > 0) {
                    return false;
                }
            }
        }
        return true;
    }

}

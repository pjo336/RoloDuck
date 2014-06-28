package com.roloduck.jpa;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.SubscriptionType;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.contact.service.ContactService;
import com.roloduck.models.partner.service.PartnerService;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.user.User;
import com.roloduck.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

/*
 * PersistenceStartupEvent performs neccessary actions on the startup of the application.
 * This includes ensuring the admin account exists and the issue types.
 */

@Component
public class PersistenceStartupEvent implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private ContactService contactService;

    private static final String ADMIN_NAME = "Admin";
    private static final String ADMIN_EMAIL = "admin@roloduck.com";
    private static final String ADMIN_PASSWORD = "admin";

    private static final String COMPANY_NAME = "RoloDuck";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // Create a company
        Company newCompany = new Company(COMPANY_NAME, SubscriptionType.PREMIUM_SUBSCRIPTION);
        try {
            newCompany = companyService.restoreCompanyByName(COMPANY_NAME);
        } catch(ServiceLogicException sle) {
            // The company doesnt exist, so add it
            try {
                companyService.createCompany(newCompany);
            } catch(ServiceLogicException e) {
                e.printStackTrace();
            }
        }
        // Create the admin user
        User newUser = new User(ADMIN_NAME, ADMIN_EMAIL, ADMIN_PASSWORD);
        try {
            newUser = userService.restoreUserByEmail(ADMIN_EMAIL);
        } catch(ServiceLogicException sle) {
            // User doesnt exist, add him
            try {
                userService.signUpUser(newUser, newCompany.getCompanyIdentifyingString());
            } catch(ServiceLogicException e) {
                e.printStackTrace();
            }
        }

        // Create a project
        Project newProject = new Project("Test Project", "This is a test project", newUser.getId(), newCompany.getId());
        try {
            List<Project> projects = projectService.findAllCompanyProjects(newCompany.getId());
            boolean projectFound = false;
            for(Project proj: projects) {
                if(proj.getCreatedByUser() == newProject.getCreatedByUser() && proj.getProjectName().equals
                        (newProject.getProjectName()) && proj.getProjectDescription().equals(newProject
                                .getProjectDescription())) {
                    projectFound = true;
                }
            }
            if(!projectFound) {
                projectService.createProject(newProject, newUser);
            }
        } catch(ServiceLogicException sle) {
            sle.printStackTrace();
        }
    }
}
package com.roloduck.models.projpartassoc.dao;

import com.roloduck.IntegrationTest;
import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.dao.PartnerDAO;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.dao.ProjectDAO;
import com.roloduck.models.projpartassoc.ProjPartAssoc;
import com.roloduck.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/30/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@IntegrationTest
@Transactional
public class ProjPartAssocDAOImplTest {

    @Autowired
    private ProjPartAssocDAO impl;
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private PartnerDAO partnerDAO;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;

    /**
     * Test that with a valid current Project and Partner, a ProjPartAssoc can be correctly inserted into the database
     * @throws ServiceLogicException
     */
    @Test
    public void testInsertAssoc() throws ServiceLogicException {
        long count = impl.count();
        insertTestProjPartAssoc("This is totes a test project", "This is totes a test partner");
        assertEquals(count + 1, impl.count());
    }

    /**
     * Test that removing an association correctly removes that association from the database
     * @throws ServiceLogicException
     */
    @Test
    public void testRemoveAssoc() throws ServiceLogicException {
        long count = impl.count();
        ProjPartAssoc assoc = insertTestProjPartAssoc("This is totes a test project", "This is totes a test partner");
        assertEquals(count + 1, impl.count());
        impl.removeAssoc(assoc);
        assertEquals(count, impl.count());
    }

    /**
     * Test that when an association is added, findPartnersByProject finds the correct partner id when given the
     * entered project id
     * @throws ServiceLogicException
     */
    @Test
    public void testFindPartnersByProjectId() throws ServiceLogicException, DAOException {
        ProjPartAssoc assoc = insertTestProjPartAssoc("This is totes a test project", "This is totes a test partner");
        long projectId = assoc.getProjectId();
        List<Long> partnerIds = impl.findPartnersByProjectId(projectId);
        long partnerId = partnerIds.get(0);
        assertEquals(assoc.getPartnerId(), partnerId);
    }

    /**
     * Test that there is no exception when an association is not found
     * @throws ServiceLogicException
     */
    @Test
    public void testFindPartnersByProjectIdNotFound() throws ServiceLogicException, DAOException {
        impl.findPartnersByProjectId(-999);
    }

    /**
     * Test that findProjectsByPartnerId returns the correct associated project to a partner id
     * @throws ServiceLogicException
     * @throws DAOException
     */
    @Test
    public void testFindProjectsByPartnerId() throws ServiceLogicException, DAOException {
        ProjPartAssoc assoc = insertTestProjPartAssoc("This is totes a test project", "This is totes a test partner");
        long partnerId = assoc.getPartnerId();
        List<Long> projectIds = impl.findProjectsByPartnerId(partnerId);
        long projectId = projectIds.get(0);
        assertEquals(assoc.getProjectId(), projectId);
    }

    /**
     * Test that there is no exception when an association is not found
     * @throws ServiceLogicException
     */
    @Test
    public void testFindProjectsByPartnerIdNotFound() throws ServiceLogicException, DAOException {
        impl.findProjectsByPartnerId(-999);
    }

    /**
     * Test that the count is updated by 1 after an insert
     * @throws ServiceLogicException
     */
    @Test
    public void testCount() throws ServiceLogicException {
        long count = impl.count();
        insertTestProjPartAssoc("This is totes a test project", "this is totes a test partner");
        long countAfter = impl.count();
        assertEquals(count + 1, countAfter);
    }

    /**
     * This inserts a testable ProjPartAssoc into the DB. Only use this inside a transaction so that it is removed
     * correctly after the test is run
     * @return a testable ProjPartAssoc
     * @throws ServiceLogicException
     */
    private ProjPartAssoc insertTestProjPartAssoc(String projectName,
                                                  String partnerName) throws ServiceLogicException {
        Project project = createProject(projectName);
        projectDAO.insertProject(project);
        Partner partner = createPartner(partnerName);
        partnerDAO.insertPartner(partner);
        ProjPartAssoc assoc =  new ProjPartAssoc();
        assoc.setProjectId(project.getId());
        assoc.setPartnerId(partner.getId());
        impl.insertAssoc(assoc);
        return assoc;
    }

    /**
     * Create a test Project. NOTE: This assumes RoloDuck company and admin user are both present in the db
     * @return the testable Project
     * @throws ServiceLogicException
     */
    private Project createProject(String projectName) throws ServiceLogicException {
        Project project = new Project();
        // This company should always be present currently
        project.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        // This user should always be present currently
        project.setCreatedByUser(userService.restoreUserByEmail("admin@roloduck.com").getId());
        project.setProjectName(projectName);
        return project;
    }

    /**
     * Create a test Partner. NOTE: This assumes RoloDuck company is present in db
     * @return the testable Partner
     * @throws ServiceLogicException
     */
    private Partner createPartner(String partnerName) throws ServiceLogicException {
        Partner partner = new Partner();
        // This company should always be present currently
        partner.setCompanyId(companyService.restoreCompanyByName("RoloDuck").getId());
        partner.setPartnerName(partnerName);
        return partner;
    }
}

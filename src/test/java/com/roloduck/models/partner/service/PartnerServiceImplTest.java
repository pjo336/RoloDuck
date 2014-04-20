package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.dao.PartnerDAO;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.models.projpartassoc.dao.ProjPartAssocDAO;
import com.roloduck.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/17/14
 * RoloDuck
 */

@RunWith(MockitoJUnitRunner.class)
public class PartnerServiceImplTest {

    @InjectMocks
    private PartnerServiceImpl impl = new PartnerServiceImpl();
    @Mock private PartnerDAO dao;
    @Mock private ProjPartAssocDAO assocDAO;
    @Mock private CompanyService companyService;
    @Mock private ProjectService projectService;

    /**
     * Test that when a valid partner and user are given, the partner's company id is correctly set to that of the
     * user's, and that a call to insertPartner occurs
     * @throws ServiceLogicException
     */
    @Test
    public void testCreatePartner() throws ServiceLogicException {
        Partner partner =  new Partner();
        partner.setPartnerName("Partner Name test");
        partner.setCompanyId(-999);
        User user = new User();
        user.setCompanyId(123);

        // Company id for partner isnt set correctly yet
        assertFalse("User and partner company id are the same, and should not be yet",
                user.getCompanyId() == partner.getCompanyId());

        impl.createPartner(partner, user);

        assertEquals("User and partner company id is NOT the same, and it should be now",
                user.getCompanyId(), partner.getCompanyId());
        // Check the partner is inserted
        verify(dao, times(1)).insertPartner(partner);
    }

    /**
     * Test that no call to insertPartner occurs when the partner is null. Instead,
     * ensure that a ServiceLogicException is thrown.
     * @throws ServiceLogicException
     */
    @SuppressWarnings("all")
    @Test(expected = ServiceLogicException.class)
    public void testCreatePartnerNullPartner() throws ServiceLogicException {
        Partner partner =  null;
        User user = new User();
        impl.createPartner(partner, user);
        // Since the partner is null, this should never be called
        verify(dao, times(0)).insertPartner(partner);
    }

    /**
     * Test that no call to insertPartner occurs when the user is null. Instead, ensure that a ServiceLogicException
     * is thrown.
     * @throws ServiceLogicException
     */
    @SuppressWarnings("all")
    @Test(expected = ServiceLogicException.class)
    public void testCreatePartnerNullUser() throws ServiceLogicException {
        Partner partner =  new Partner();
        User user = null;
        impl.createPartner(partner, user);
        // Since the user is null, this should never be called
        verify(dao, times(0)).insertPartner(partner);
    }

    //TODO move this to the projpartassocservice test

//    /**
//     * Test that when a valid partner and project id are used, a call to insertAssoc is correctly performed.
//     * @throws ServiceLogicException
//     */
//    @Test
//    public void testAssignPartnerToProject() throws ServiceLogicException {
//        Partner partner = new Partner();
//        partner.setId(123);
//        Project project = new Project();
//        project.setId(123);
//        when(projectService.restoreProjectById(project.getId())).thenReturn(project);
//        impl.assignPartnerToProject(partner, project.getId());
//        verify(assocDAO, times(1)).insertAssoc(any(ProjPartAssoc.class));
//    }
//
//    /**
//     * Since the attached project is not valid, this should throw a ServiceLogicException
//     * @throws ServiceLogicException
//     */
//    @Test(expected = ServiceLogicException.class)
//    public void testAssignPartnerToProjectNonExistentProject() throws ServiceLogicException {
//        Partner partner = new Partner();
//        partner.setId(123);
//        long projectId = -999;
//        //when(projectService.restoreProjectById(projectId)).thenThrow(ServiceLogicException.class);
//        impl.assignPartnerToProject(partner, projectId);
//        //verify(assocDAO, times(1)).insertAssoc(any(ProjPartAssoc.class));
//    }

    @Test
    public void testFindAllCompanyPartners() throws ServiceLogicException {
        long companyId = 123;
        Company company = new Company();
        company.setId(companyId);
        when(companyService.restoreCompanyById(companyId)).thenReturn(company);
        impl.findAllCompanyPartners(companyId);
        verify(dao, times(1)).findPartnersByCompanyId(companyId);
    }
}

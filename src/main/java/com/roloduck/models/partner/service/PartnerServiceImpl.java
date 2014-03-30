package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.dao.PartnerDAO;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.models.projpartassoc.ProjPartAssoc;
import com.roloduck.models.projpartassoc.dao.ProjPartAssocDAO;
import com.roloduck.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PartnerServiceImpl implements PartnerService {

    static final Logger logger = LoggerFactory.getLogger(PartnerServiceImpl.class);

    @Autowired
    private PartnerDAO partnerDAO;
    @Autowired
    private ProjPartAssocDAO assocDAO;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProjectService projectService;

    @Override
    public void createPartner(Partner partner, User user) throws ServiceLogicException {
        if(partner != null && user != null) {
            partner.validate();
            partner.setCompanyId(user.getCompanyId());
            partnerDAO.insertPartner(partner);
        } else {
            if(partner == null) {
                logger.error("The partner being created was null");
                throw new ServiceLogicException("The partner being created was null");
            } else if(user ==  null) {
                logger.error("The current user is not valid to create a partner");
                throw new ServiceLogicException("The current user is not valid to create a partner");
            }
        }
    }

    @Override
    public void assignPartnerToProject(Partner partner, long projectId) throws ServiceLogicException {
        // Ensure the project exists, exception will be thrown if not
        projectService.restoreProjectById(projectId);

        if(partner != null) {
            ProjPartAssoc assoc = new ProjPartAssoc();
            assoc.setProjectId(projectId);
            assoc.setPartnerId(partner.getId());
            assocDAO.insertAssoc(assoc);
        } else {
            logger.error("The partner being assigned does not exist.");
            throw new ServiceLogicException("The partner being assigned does not exist.");
        }
    }

    @Override
    public List<Partner> findAllCompanyPartners(long companyId) throws ServiceLogicException {
        if(companyService.restoreCompanyById(companyId) != null) {
            return partnerDAO.findPartnersByCompanyId(companyId);
        } else {
            logger.error("Company with id: " + companyId + " does not exist");
            throw new ServiceLogicException("Company could not be found.");
        }
    }

    @Override
    public List<Partner> findAllProjectPartners(long projectId) {
        return null;
    }
}

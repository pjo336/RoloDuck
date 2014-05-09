package com.roloduck.models.partner.service;

import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.contact.dao.ContactDAO;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.dao.PartnerDAO;
import com.roloduck.models.project.Project;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.models.projpartassoc.dao.ProjPartAssocDAO;
import com.roloduck.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private ContactDAO contactDAO;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProjectService projectService;

    @Override
    public void createPartner(Partner partner, User user) throws ServiceLogicException {
        if(partner != null && user != null) {
            partner.setCompanyId(user.getCompanyId());
            partner.validate();
            partnerDAO.insertPartner(partner);
        } else {
            if(partner == null) {
                logger.error("The partner being created was null");
                throw new ServiceLogicException("The partner being created was null");
            } else {
                logger.error("The current user is not valid to create a partner");
                throw new ServiceLogicException("The current user is not valid to create a partner");
            }
        }
    }

    @Override
    public Partner restoreById(long partnerId) throws ServiceLogicException {
        try {
            Partner partner = partnerDAO.restoreById(partnerId);
            if(partner != null) {
                return partner;
            }
        } catch (DAOException de) {
            logger.error("Partner with ID: " + partnerId + " not found.");
            throw new ServiceLogicException(de);
        }
        logger.error("There was a problem restoring a partner by ID: " + partnerId);
        throw new ServiceLogicException("There was a problem restoring a partner by ID: " + partnerId);
    }

    @Override
    public List<Partner> findAllCompanyPartners(long companyId) throws ServiceLogicException {
        return findAllCompanyPartners(companyId, false);
    }

    @Override
    public List<Partner> findAllCompanyPartnersSortedAlphabetically(long companyId) throws ServiceLogicException {
        return findAllCompanyPartners(companyId, true);
    }

    private List<Partner> findAllCompanyPartners(long companyId, boolean toSort) throws ServiceLogicException {
        if(companyService.restoreCompanyById(companyId) != null) {
            try {
                return partnerDAO.findPartnersByCompanyId(companyId, toSort);
            } catch (DAOException de) {
                logger.error(de.getMessage());
                throw new ServiceLogicException(de.getMessage());
            }
        } else {
            logger.error("Company with id: " + companyId + " does not exist");
            throw new ServiceLogicException("Company could not be found.");
        }
    }

    @Override
    public List<Partner> findAllProjectPartners(long projectId) {
        return null;
    }

    @Override
    public List<Project> findAllConnectedProjects(long partnerId) throws ServiceLogicException {
        List<Project> connectedProjects = new ArrayList<>();
        if(restoreById(partnerId) != null) {
            try {
                List<Long> projectIds = assocDAO.findProjectsByPartnerId(partnerId);
                for(Long l: projectIds) {
                    connectedProjects.add(projectService.restoreProjectById(l));
                }
            } catch(DAOException de) {
                throw new ServiceLogicException("Associations of the partner with id: " + partnerId + " could not be " +
                        "found.");
            }
        }
        return connectedProjects;
    }

    @Override
    public List<Contact> findAllAssociatedContacts(long partnerId) throws ServiceLogicException {
        List<Contact> associatedContacts = new ArrayList<>();
        if(restoreById(partnerId) != null) {
            associatedContacts = contactDAO.findContactsByPartnerId(partnerId);
        }
        return associatedContacts;
    }

    @Override
    public void removePartner(long partnerId) throws ServiceLogicException {
        Partner partner = restoreById(partnerId);
        partnerDAO.removePartner(partner);
    }
}

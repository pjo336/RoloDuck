package com.roloduck.models.projpartassoc.service;

import com.roloduck.exception.DAOException;
import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.service.PartnerService;
import com.roloduck.models.project.service.ProjectService;
import com.roloduck.models.projpartassoc.ProjPartAssoc;
import com.roloduck.models.projpartassoc.dao.ProjPartAssocDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 4/19/14
 * RoloDuck
 */

@Service
public class ProjPartAssocServiceImpl implements ProjPartAssocService {

    static final Logger logger = LoggerFactory.getLogger(ProjPartAssocServiceImpl.class);

    @Autowired
    private ProjPartAssocDAO assocDAO;

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private ProjectService projectService;

    @Override
    public void assignPartnerToProject(long partnerId, long projectId) throws ServiceLogicException {
        // Ensure the project exists, exception will be thrown if not
        projectService.restoreProjectById(projectId);
        // Ensure the partner exists
        partnerService.restoreById(partnerId);

        // Create the association
        ProjPartAssoc assoc = new ProjPartAssoc();
        assoc.setProjectId(projectId);
        assoc.setPartnerId(partnerId);

        // Now validate the association, then check if one already exists
        try {
            assoc.validate();
            if(validateUniqueConstraintProjectPartner(assoc)) {
                assocDAO.insertAssoc(assoc);
            }
        } catch (ServiceLogicException sle) {
            logger.error(sle.getMessage());
            throw sle;
        }
    }

    @Override
    public void unassignPartnerFromProject(long partnerId, long projectId) throws ServiceLogicException {
        // Ensure the project exists, exception will be thrown if not
        try {
            projectService.restoreProjectById(projectId);
            partnerService.restoreById(partnerId);
            ProjPartAssoc assoc = assocDAO.findAssoc(projectId, partnerId);
            if(assoc != null) {
                assocDAO.removeAssoc(assoc);
            }
        } catch (DAOException de) {
            throw new ServiceLogicException(de.getMessage());
        } catch (ServiceLogicException sle) {
            logger.error("Problem while unassigning partner from project, message: " + sle.getMessage());
            throw sle;
        }
    }

    /**
     * Check if the given association is valid to enter without hitting a constraint violation. If it does, catch it
     * and throw a ServiceLogicException
     * @param association the association to validate
     * @return true if valid
     */
    private boolean validateUniqueConstraintProjectPartner(ProjPartAssoc association) throws ServiceLogicException {
        try {
            return assocDAO.validateUniqueProjectPartnerConstraint(association);
        } catch(DAOException de) {
            throw new ServiceLogicException(de.getMessage());
        }
    }
}

package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.dao.PartnerDAO;
import com.roloduck.models.projpartassoc.ProjPartAssoc;
import com.roloduck.models.projpartassoc.dao.ProjPartAssocDAO;
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
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerDAO partnerDAO;
    @Autowired
    private ProjPartAssocDAO assocDAO;

    @Override
    public void createPartner(Partner partner, User user) throws ServiceLogicException {
        if(partner != null) {
            partner.setCompanyId(user.getCompanyId());
            partnerDAO.insertPartner(partner);
        }
    }

    @Override
    public void assignPartnerToProject(Partner partner, long projectId) throws ServiceLogicException {
        if(partner != null) {
            ProjPartAssoc assoc = new ProjPartAssoc();
            assoc.setProjectId(projectId);
            assoc.setPartnerId(partner.getId());
            assocDAO.insertAssoc(assoc);
        }
    }

    @Override
    public List<Partner> findAllPartners() {
        return null;
    }

    @Override
    public List<Partner> findAllProjectPartners(long projectId) {
        return null;
    }
}

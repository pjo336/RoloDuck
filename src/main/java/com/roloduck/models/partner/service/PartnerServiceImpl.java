package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.dao.PartnerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void createPartner(Partner partner, long projectId) throws ServiceLogicException {
        if(partner != null) {
            partnerDAO.insertPartner(partner);
            partnerDAO.insertPartnerProjectAssoc(partner, projectId);
        }
    }

    @Override
    public List<Partner> findAllProjectPartners(long projectId) {
        return null;
    }
}

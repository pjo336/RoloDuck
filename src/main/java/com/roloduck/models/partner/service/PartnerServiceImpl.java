package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.dao.PartnerDAO;
import com.roloduck.models.partner.model.Partner;
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

    @Override
    public void createPartner(Partner partner) throws ServiceLogicException {
        if(partner != null) {
            partnerDAO.insertPartner(partner);
        }
    }

    @Override
    public List<Partner> findAllProjectPartners(long projectId) {
        return null;
    }
}

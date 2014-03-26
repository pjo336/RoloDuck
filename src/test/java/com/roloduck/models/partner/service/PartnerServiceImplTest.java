package com.roloduck.models.partner.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.partner.Partner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/17/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@Transactional
public class PartnerServiceImplTest {

    @Autowired
    private PartnerService partnerService;

    @Test
    public void testCreatePartner() throws ServiceLogicException {
        Partner partner =  new Partner();
        partner.setPartnerName("Partner Name test");
        // TODO write this test
        //partnerService.createPartner(partner);
    }
}

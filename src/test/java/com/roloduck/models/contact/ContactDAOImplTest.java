package com.roloduck.models.contact;

import com.roloduck.models.contact.dao.ContactDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/25/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@Transactional
public class ContactDAOImplTest {

    @Autowired
    private ContactDAO impl;

    @Test
    public void testFindContactsByCompanyId() {
        impl.findContactsByCompanyId(-999);
    }

}

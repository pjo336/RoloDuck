package com.roloduck.models.company.dao;

import com.roloduck.exception.DAOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@Transactional
public class CompanyDAOImplTest {

    @Autowired
    CompanyDAO impl;

    // Our expected exception rule
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    /**
     * Test that the exception type thrown by our abstract DAO is correctly morphed into a DAOException
     * @throws DAOException
     */
    @Test
    public void testExceptionTypeOnEmptyDataReturn() throws DAOException {
        thrown.expect(DAOException.class);
        try {
            impl.restoreById(-1);
        } catch(DAOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}

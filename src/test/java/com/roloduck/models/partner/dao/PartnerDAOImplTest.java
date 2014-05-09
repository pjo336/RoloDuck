package com.roloduck.models.partner.dao;

import com.roloduck.IntegrationTest;
import com.roloduck.exception.DAOException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.SubscriptionType;
import com.roloduck.models.company.dao.CompanyDAO;
import com.roloduck.models.partner.Partner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 5/4/14
 * RoloDuck
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/applicationContext.xml",
        "classpath:/config/applicationContext-security.xml",
        "classpath:/config/applicationContext-JPA.xml"})
@IntegrationTest
@Transactional
public class PartnerDAOImplTest {

    @Autowired
    private PartnerDAO impl;

    @Autowired
    private CompanyDAO companyDAO;

    /**
     * Test that all partners attached to a company are correctly returned by this method
     */
    @Test
    public void testFindPartnersByCompanyId() throws DAOException {
        Company company = getAttachedCompany();
        Partner partner1 = createPartner("aaaaPartner1", company.getId());
        impl.insertPartner(partner1);
        Partner partner2 = createPartner("zzzzPartner2", company.getId());
        impl.insertPartner(partner2);
        Partner partner3 = createPartner("qqqqPartner3", company.getId());
        impl.insertPartner(partner3);

        // Now test unsorted retrievals
        List<Partner> partners = impl.findPartnersByCompanyId(company.getId(), false);
        boolean partner1Seen = false;
        boolean partner2Seen = false;
        boolean partner3Seen = false;
        for(Partner p: partners) {
            if(p.getId() == partner1.getId() && p.getPartnerName().equals(partner1.getPartnerName())) {
                partner1Seen = true;
            }
            if(p.getId() == partner2.getId() && p.getPartnerName().equals(partner2.getPartnerName())) {
                partner2Seen = true;
            }
            if(p.getId() == partner3.getId() && p.getPartnerName().equals(partner3.getPartnerName())) {
                partner3Seen = true;
            }
        }
        assertTrue(partner1Seen);
        assertTrue(partner2Seen);
        assertTrue(partner3Seen);

        // Now test the sort
        List<Partner> sortedPartners = impl.findPartnersByCompanyId(company.getId(), true);
        assertTrue(checkIfListOfPartnersIsSortedByName(sortedPartners));
    }

    /**
     * Return a company that exists in the database for testing
     * @return the company that exists
     */
    private Company getAttachedCompany() {
        List<Company> companies = companyDAO.find();
        if(companies.size() > 0) {
            return companies.get(0);
        } else {
            Company newCompany = new Company(UUID.randomUUID().toString(), SubscriptionType.FREE_SUBSCRIPTION);
            companyDAO.insertCompany(newCompany);
            return newCompany;
        }
    }

    private Partner createPartner(String name, long companyId) {
        Partner partner =  new Partner();
        partner.setCompanyId(companyId);
        partner.setPartnerName(name);
        return partner;
    }

    private boolean checkIfListOfPartnersIsSortedByName(List<Partner> listToCheckSort) {
        for(int i = 0; i < listToCheckSort.size(); i++) {
            if(i + 1 < listToCheckSort.size()) {
                String partnerName = listToCheckSort.get(i).getPartnerName();
                String partnerName2 = listToCheckSort.get(i + 1).getPartnerName();
                int compare = partnerName.compareToIgnoreCase(partnerName2);
                if(compare > 0) {
                    return false;
                }
            }
        }
        return true;
    }
}

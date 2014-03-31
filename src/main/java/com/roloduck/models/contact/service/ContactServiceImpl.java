package com.roloduck.models.contact.service;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.contact.dao.ContactDAO;
import com.roloduck.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/25/14
 * RoloDuck
 */

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private CompanyService companyService;

    @Override
    public void createContact(Contact contact, User user) throws ServiceLogicException {
        if(contact != null) {
            // TODO hardcoded partner
            contact.setPartnerId(1);
            contact.setCompanyId(user.getCompanyId());
            contact.validate();
            contactDAO.insertContact(contact);
        }
    }

    @Override
    public List<Contact> findAllCompanyContacts(long companyId) throws ServiceLogicException {
        if(companyService.restoreCompanyById(companyId) != null) {
            return contactDAO.findContactsByCompanyId(companyId);
        } else {
            throw new ServiceLogicException("The company with id: " + companyId + " does not exist");
        }
    }
}

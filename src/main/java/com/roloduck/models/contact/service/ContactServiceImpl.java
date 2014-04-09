package com.roloduck.models.contact.service;

import com.roloduck.exception.DAOException;
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
    public void createContact(Contact contact, User user, long partnerId) throws ServiceLogicException {
        if(contact != null) {
            // TODO hardcoded partner
            contact.setPartnerId(partnerId);
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

    @Override
    public Contact restoreById(long id) throws ServiceLogicException {
        try {
            return contactDAO.restoreById(id);
        } catch(DAOException de) {
            throw new ServiceLogicException("There was a problem retrieving the contact with id: " + id);
        }
    }

    @Override
    public void removeContact(Contact contact) {
        if(contact != null) {
            contactDAO.removeContact(contact);
        }
    }
}

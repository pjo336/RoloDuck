package com.roloduck.web.contact;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.contact.service.ContactService;
import com.roloduck.user.User;
import com.roloduck.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/21/14
 * RoloDuck
 */

@Controller
public class ContactController {

    static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private CompanyService companyService;

    private static final String URI_PREFIX = "/contacts";

    @RequestMapping(value = URI_PREFIX)
    public String serveContacts(ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
            // Add the current user, his company, and the list of his company's partners to the model
            model.addAttribute("user", user);
            model.addAttribute("company", companyService.restoreCompanyById(user.getCompanyId()));
            model.addAttribute("contacts", contactService.findAllCompanyContacts(user.getCompanyId()));
        } catch(ServiceLogicException e) {
            logger.error("There was a problem trying to serve contacts.");
        }
        return "contacts";
    }


    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.GET)
    public String serveContactCreate(ModelMap model) {
        User user = null;
        Company company = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException e) {
            // TODO propogate this to the front end?
            logger.warn("An anonymous user attempted to access /partners/create.");
            return "redirect:/";
        }
        try {
            company = companyService.restoreCompanyByUser(user);
            model.addAttribute("companyName", company.getCompanyName());
            return "contacts-create";
        } catch(ServiceLogicException ble) {
            logger.error("The user: " + user.getEmail() + " does not belong to a company. Rerouting to index.");
            return "redirect:/";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.POST)
    public String postContactCreate(@ModelAttribute("contact") Contact contact, ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException e) {
            logger.error("An anonymous user attempted to create a contact.");
        }
        try {
            contactService.createContact(contact, user);
        } catch(ServiceLogicException e) {
            logger.error("There was a problem creating the contact named: " + contact.getContactFirstName() + " " +
                    contact.getContactLastName());
        }
        return "redirect:/contacts";
    }
}

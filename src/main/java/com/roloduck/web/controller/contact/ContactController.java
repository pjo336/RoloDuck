package com.roloduck.web.controller.contact;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.contact.service.ContactService;
import com.roloduck.user.User;
import com.roloduck.utils.SecurityUtils;
import com.roloduck.web.exception.ProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/21/14
 * RoloDuck
 */

@Controller
@SessionAttributes("companyName")
public class ContactController extends ProcessException {

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
            model.addAttribute("companyName", companyService.restoreCompanyById(user.getCompanyId()).getCompanyName());
            model.addAttribute("contacts", contactService.findAllCompanyContacts(user.getCompanyId()));
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "contacts";
    }


    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.GET)
    public String serveContactCreate(ModelMap model) {
        User user = null;
        Company company = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "redirect:/";
        }
        try {
            company = companyService.restoreCompanyByUser(user);
            model.addAttribute("companyName", company.getCompanyName());
            return "contacts-create";
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "redirect:/";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.POST)
    public String postContactCreate(@ModelAttribute("contact") Contact contact, ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        try {
            contactService.createContact(contact, user);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "contacts-create";
        }
        return "redirect:/contacts";
    }
}

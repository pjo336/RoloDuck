package com.roloduck.web.controller.contact;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.contact.service.ContactService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.service.PartnerService;
import com.roloduck.user.User;
import com.roloduck.utils.JSONUtils;
import com.roloduck.utils.SecurityUtils;
import com.roloduck.web.converter.ContactPartnerConverter;
import com.roloduck.web.exception.ProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private PartnerService partnerService;

    private static final String URI_PREFIX = "/contacts";

    @RequestMapping(value = URI_PREFIX)
    public String serveContacts(ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
            // Add the current user, his company, and the list of his company's partners to the model
            model.addAttribute("user", user);
            model.addAttribute("companyName", companyService.restoreCompanyById(user.getCompanyId()).getCompanyName());
            List<Contact> contacts = contactService.findAllCompanyContacts(user.getCompanyId());
            List<ContactPartnerConverter> contactPartnerConverters = new ArrayList<>();
            for(Contact c: contacts) {
                Partner linkedPartner = partnerService.restoreById(c.getPartnerId());
                ContactPartnerConverter converter = new ContactPartnerConverter(
                        linkedPartner.getId(), linkedPartner.getPartnerDescription(), linkedPartner.getPartnerName(),
                        c.getContactPhone(), c.getContactEmail(), c.getContactTitle(), c.getContactLastName(),
                        c.getContactFirstName(), c.getId()
                );
                contactPartnerConverters.add(converter);
            }
            model.addAttribute("contacts", contactPartnerConverters);

        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "contacts";
    }

    @RequestMapping(value = URI_PREFIX + "/create/partner={partnerStr}", method = RequestMethod.GET)
    public String serveContactCreatePopulatedPartner(@PathVariable String partnerStr, ModelMap model) {
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
            List<Partner> companyPartners = partnerService.findAllCompanyPartners(company.getId());
            model.addAttribute("partners", companyPartners);
            model.addAttribute("partnerSelected", Long.valueOf(partnerStr));
            return "contacts-form";
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "redirect:/";
        }
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
            List<Partner> companyPartners = partnerService.findAllCompanyPartners(company.getId());
            model.addAttribute("partners", companyPartners);
            return "contacts-form";
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "redirect:/";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.POST)
    public String postContactCreate(@ModelAttribute("converter") ContactPartnerConverter converter, ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        try {
            Contact contact = new Contact();
            // TODO make constructor for this
            contact.setContactFirstName(converter.getContactFirstName());
            contact.setContactLastName(converter.getContactLastName());
            contact.setContactTitle(converter.getContactTitle());
            contact.setContactEmail(converter.getContactEmail());
            contact.setContactPhone(converter.getContactPhone());
            Partner partner = new Partner();
            if(converter.getPartnerId() > 0) {
                contactService.createContact(contact, user, converter.getPartnerId());
            } else {
                if(converter.getPartnerName() != null && !converter.getPartnerName().equalsIgnoreCase("")) {
                    partner.setPartnerName(converter.getPartnerName());
                    partner.setPartnerDescription(converter.getPartnerDescription());
                    partnerService.createPartner(partner, user);
                    contactService.createContact(contact, user, partner.getId());
                }
            }
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "contacts-form";
        }
        return "redirect:/contacts";
    }

    @RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
    public void postDeleteContact(ModelMap model, HttpServletRequest request, HttpServletResponseWrapper response) {
        try {
            Contact contact = contactService.restoreById(Long.valueOf(request.getParameter("contactId")));
            contactService.removeContact(contact);
            model.addAttribute("isValid", true);
        } catch (ServiceLogicException sle) {
            model.addAttribute("isValid", false);
            // TODO write the exception back to the javascript
            processRDException(model, sle);
        }
        try {
            JSONUtils.write(response, model);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @RequestMapping(value = URI_PREFIX + "/edit={contactId}", method = RequestMethod.GET)
    public String serveContactEdit(@PathVariable long contactId, ModelMap model) {
        User user = null;
        Company company = null;

        try {
            user = SecurityUtils.getCurrentUser();
            company = companyService.restoreCompanyByUser(user);
            model.addAttribute("companyName", company.getCompanyName());
            List<Partner> companyPartners = partnerService.findAllCompanyPartners(company.getId());
            model.addAttribute("partners", companyPartners);
            Contact contact = contactService.restoreById(contactId);
            model.addAttribute("contact", contact);
            return "contacts-form";
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            return "redirect:/";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/edit", method = RequestMethod.POST)
    public String postContactEdit(@ModelAttribute Contact contact, ModelMap model) {
        try {
            SecurityUtils.getCurrentUser();
            contactService.updateContact(contact);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            // Return back to the create page with the error message
            return "contacts-form";
        }
        return "redirect:/contacts";
    }
}

package com.roloduck.web.controller.partner;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.service.PartnerService;
import com.roloduck.user.User;
import com.roloduck.utils.SecurityUtils;
import com.roloduck.web.exception.ProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/partners")
@SessionAttributes("companyName")
public class PartnerController extends ProcessException {

    static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private PartnerService partnerService;

    private static final String URI_PREFIX = "/partners";

    @RequestMapping
    public String servePartners(ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
            // Add the current user, his company, and the list of his company's partners to the model
            //model.addAttribute("user", user);
            model.addAttribute("companyName", companyService.restoreCompanyById(user.getCompanyId()).getCompanyName());
            model.addAttribute("partners", partnerService.findAllCompanyPartners(user.getCompanyId()));
        } catch(ServiceLogicException e) {
            logger.error("There was a problem trying to serve Partners");
            processRDException(model, e);
        }
        return "partners";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String servePartnerCreate(ModelMap model) {
        User user = null;
        Company company = null;
        long companyId = -999;
        try {
            user = SecurityUtils.getCurrentUser();
            companyId = user.getCompanyId();
            company = companyService.restoreCompanyById(companyId);
            model.addAttribute("companyName", company.getCompanyName());
        } catch(ServiceLogicException ble) {
            logger.error("Company with id: " + companyId + " was not found in create partner");
            processRDException(model, ble);
        }
        return "partners-create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String postPartnerCreate(@ModelAttribute("partner") Partner partner, ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            partnerService.createPartner(partner, user);
        } catch(ServiceLogicException e) {
            logger.error(e.getMessage());
            processRDException(model, e);
            return "partners-create";
        }
        return "redirect:/partners";
    }
}

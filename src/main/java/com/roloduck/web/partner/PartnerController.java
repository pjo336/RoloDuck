package com.roloduck.web.partner;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.service.PartnerService;
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
public class PartnerController {

    static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private PartnerService partnerService;

    private static final String URI_PREFIX = "/partners";

    @RequestMapping(value = URI_PREFIX)
    public String servePartners(ModelMap model) {
        return "partners";
    }

    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.GET)
    public String servePartnerCreate(ModelMap model) {
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
            return "partners-create";
        } catch(ServiceLogicException ble) {
            logger.error("The user: " + user.getEmail() + " does not belong to a company. Rerouting to index.");
            return "redirect:/";
        }
    }

    @RequestMapping(value = URI_PREFIX + "/create", method = RequestMethod.POST)
    public String postPartnerCreate(@ModelAttribute("partner") Partner partner, ModelMap model) {
        User user = null;
        try {
            user = SecurityUtils.getCurrentUser();
        } catch(ServiceLogicException e) {
            logger.error("An anonymous user attempted to create a partner.");
        }
        try {
            partnerService.createPartner(partner, user);
        } catch(ServiceLogicException e) {
            logger.error("There was a problem creating the partner named: " + partner.getPartnerName());
        }
        return "redirect:/partners";
    }
}

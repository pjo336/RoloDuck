package com.roloduck.web.controller.partner;

import com.roloduck.exception.ServiceLogicException;
import com.roloduck.models.company.Company;
import com.roloduck.models.company.service.CompanyService;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.service.PartnerService;
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
@RequestMapping("/partners")
@SessionAttributes("companyName")
public class PartnerController extends ProcessException {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private PartnerService partnerService;

    @RequestMapping
    public String servePartners(ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            // Add the current company, and the list of company's partners to the model
            model.addAttribute("companyName", companyService.restoreCompanyById(user.getCompanyId()).getCompanyName());
            model.addAttribute("partners", partnerService.findAllCompanyPartners(user.getCompanyId()));
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "partners";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String servePartnerCreate(ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            Company company = companyService.restoreCompanyById(user.getCompanyId());
            model.addAttribute("companyName", company.getCompanyName());
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
        }
        return "partners-create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String postPartnerCreate(@ModelAttribute("partner") Partner partner, ModelMap model) {
        try {
            User user = SecurityUtils.getCurrentUser();
            partnerService.createPartner(partner, user);
        } catch(ServiceLogicException sle) {
            processRDException(model, sle);
            // Return back to the create page with the error message
            return "partners-create";
        }
        return "redirect:/partners";
    }
}

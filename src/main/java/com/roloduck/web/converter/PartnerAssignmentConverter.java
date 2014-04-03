package com.roloduck.web.converter;

import com.roloduck.models.partner.Partner;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 4/1/14
 * RoloDuck
 */

public class PartnerAssignmentConverter {

    private Partner partner;
    private long projectId;

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}

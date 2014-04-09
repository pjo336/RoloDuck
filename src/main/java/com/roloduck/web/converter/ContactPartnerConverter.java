package com.roloduck.web.converter;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/31/14
 * RoloDuck
 */

public class ContactPartnerConverter {

    private long contactId;
    private String contactFirstName;
    private String contactLastName;
    private String contactTitle;
    private String contactEmail;
    private String contactPhone;
    private String partnerName;
    private String partnerDescription;
    private long partnerId;

    public ContactPartnerConverter(long partnerId, String partnerDescription, String partnerName, String contactPhone,
                                   String contactEmail, String contactTitle, String contactLastName,
                                   String contactFirstName) {
        this.partnerId = partnerId;
        this.partnerDescription = partnerDescription;
        this.partnerName = partnerName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.contactTitle = contactTitle;
        this.contactLastName = contactLastName;
        this.contactFirstName = contactFirstName;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerDescription() {
        return partnerDescription;
    }

    public void setPartnerDescription(String partnerDescription) {
        this.partnerDescription = partnerDescription;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }
}

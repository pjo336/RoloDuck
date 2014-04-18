package com.roloduck.models.contact.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.contact.Contact;
import com.roloduck.models.contact.ContactMapper;
import com.roloduck.utils.SQLUtils;
import com.roloduck.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/25/14
 * RoloDuck
 */

@Repository
public class ContactDAOImpl extends RoloDuckEntityDAOImpl<Contact> implements ContactDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final String TABLE_NAME = "RD_CONTACT";

    @Override
    public void insertContact(Contact contact) {
        super.insert(contact);
    }

    @Override
    public Contact restoreById(long id) throws DAOException {
        return super.restoreById(id, new Contact());
    }

    @Override
    public List<Contact> findContactsByCompanyId(long companyId) {
        Contact contact = new Contact();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(contact.getAllColumnNames()) + " FROM " +
                TABLE_NAME + " where company_id = ?";
        SQLUtils.printSQL(SQL);
        return jdbcTemplateObject.query(SQL, new Object[]{companyId}, new ContactMapper());
    }

    @Override
    public List<Contact> findContactsByPartnerId(long partnerId) {
        Contact contact = new Contact();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(contact.getAllColumnNames()) + " FROM " +
                TABLE_NAME + " where partner_id = ?";
        SQLUtils.printSQL(SQL);
        return jdbcTemplateObject.query(SQL, new Object[]{partnerId}, new ContactMapper());    }

    @Override
    public void removeContact(Contact contact) {
        super.remove(contact);
    }

    @Override
    public long count() {
        return super.count(new Contact());
    }
}

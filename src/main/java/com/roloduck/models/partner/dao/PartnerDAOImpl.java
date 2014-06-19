package com.roloduck.models.partner.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.PartnerMapper;
import com.roloduck.utils.SQLUtils;
import com.roloduck.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/16/14
 * RoloDuck
 */

@Repository
public class PartnerDAOImpl extends RoloDuckEntityDAOImpl<Partner> implements PartnerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final String TABLE_NAME = "RD_PARTNER";

    @Override
    public void insertPartner(Partner partner) {
        super.insert(partner);
    }

    @Override
    public Partner restoreById(long id) throws DAOException {
        return super.restoreById(id, new Partner());
    }

    @Override
    public List<Partner> findPartnersByCompanyId(long companyId, boolean toSort) throws DAOException {
        Partner partner = new Partner();
        String query = "SELECT " + StringUtils.convertStrArrToSQLColStr(partner.getAllColumnNames()) + " FROM " +
                TABLE_NAME + " where company_id = ?";
        if(toSort) {
            query += " ORDER BY partner_name";
        }
        final String SQL  = query;
        SQLUtils.printSQL(SQL);
        try {
            return jdbcTemplateObject.query(SQL, new Object[]{companyId}, new PartnerMapper());
        } catch (DataAccessException dae) {
            throw new DAOException("There was a data access exception while finding partners by company id.");
        }
    }

    @Override
    public void removePartner(Partner partner) {
        super.remove(partner);
    }

    @Override
    public void updatePartner(Partner partner) throws DAOException {
        final String SQL = "UPDATE " + TABLE_NAME + " SET partner_name = ?, partner_description = ? where id = ?";
        SQLUtils.printSQL(SQL);
        try {
            jdbcTemplateObject.update(SQL, partner.getPartnerName(), partner.getPartnerDescription(), partner.getId());
        } catch(DataAccessException dae) {
            throw new DAOException("There was a data access exception while updating a partner.");
        }
    }
}

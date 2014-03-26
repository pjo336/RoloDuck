package com.roloduck.models.partner.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.partner.PartnerMapper;
import com.roloduck.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Partner> findPartnersByCompanyId(long companyId) {
        Partner partner = new Partner();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(partner.getAllColumnNames()) + " FROM " +
                TABLE_NAME + " where company_id = ?";
        return jdbcTemplateObject.query(SQL, new Object[]{companyId}, new PartnerMapper());
    }

    @Override
    public void removePartner(Partner partner) {
        super.remove(partner);
    }
}

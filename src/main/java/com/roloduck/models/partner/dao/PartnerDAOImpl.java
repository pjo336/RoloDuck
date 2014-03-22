package com.roloduck.models.partner.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.partner.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}

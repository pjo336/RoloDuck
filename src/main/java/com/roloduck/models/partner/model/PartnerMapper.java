package com.roloduck.models.partner.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/8/14
 * RoloDuck
 */

public class PartnerMapper implements RowMapper<Partner> {

    @Override
    public Partner mapRow(ResultSet resultSet, int i) throws SQLException {
        Partner partner = new Partner();
        partner.setId(resultSet.getLong("id"));
        partner.setPartnerName(resultSet.getString("partner_name"));
        partner.setPartnerDescription(resultSet.getString("partner_description"));
        return partner;
    }
}

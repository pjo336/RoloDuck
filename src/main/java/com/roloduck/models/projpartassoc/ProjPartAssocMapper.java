package com.roloduck.models.projpartassoc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/22/14
 * RoloDuck
 */

public class ProjPartAssocMapper implements RowMapper<ProjPartAssoc> {

    @Override
    public ProjPartAssoc mapRow(ResultSet resultSet, int i) throws SQLException {
        ProjPartAssoc assoc = new ProjPartAssoc();
        assoc.setId(resultSet.getLong("id"));
        assoc.setProjectId(resultSet.getLong("project_id"));
        assoc.setPartnerId(resultSet.getLong("partner_id"));
        return assoc;
    }
}

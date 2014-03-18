package com.roloduck.models.partner.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.partner.model.Partner;
import com.roloduck.models.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
    private static final String ASSOC_TABLE_NAME = "RD_PROJECT_PARTNER_ASSOC";

    @Override
    public void insertPartner(Partner partner) {
        super.insert(partner);
    }

    @Override
    public void insertPartnerProjectAssoc(final Partner partner, final long projectId) {
        // TODO this should probably be its own entity, ie PROJECTPARTNERASSOC entity
        final String cols = "(PROJECT_ID, PARTNER_ID)";
        final String SQL = "INSERT INTO " + ASSOC_TABLE_NAME + cols + " VALUES (?, ?)";
        jdbcTemplateObject.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
                ps.setLong(1, projectId);
                ps.setLong(2, partner.getId());
                return ps;
            }
        });
    }

    @Override
    public Partner restoreById(long id) throws DAOException {
        return super.restoreById(id, new Partner());
    }

    @Override
    public List<Partner> findPartnersByProjectId(long projectId) {
        // TODO
        final String SQL = "SELECT * FROM " + ASSOC_TABLE_NAME + " where project_id = ?";
        Partner partner = new Partner();
        List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(SQL, new Object[]{projectId});
        for (Map row : rows) {
            Project project = new Project();
            project.setId((Long) (row.get("PROJECT_ID")));

        }
        return null;
    }
}

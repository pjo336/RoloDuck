package com.roloduck.models.projpartassoc.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.models.partner.Partner;
import com.roloduck.models.project.Project;
import com.roloduck.models.projpartassoc.ProjPartAssoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/22/14
 * RoloDuck
 */

@Repository
public class ProjPartAssocDAOImpl extends RoloDuckEntityDAOImpl<ProjPartAssoc>
        implements ProjPartAssocDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertAssoc(ProjPartAssoc assoc) {
        super.insert(assoc);
    }


    @Override
    public List<Partner> findPartnersByProjectId(long projectId) {
        // TODO
        ProjPartAssoc assoc = new ProjPartAssoc();
        final String SQL = "SELECT * FROM " + assoc.getTableName() + " where project_id = ?";
        Partner partner = new Partner();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL, new Object[]{projectId});
        for (Map row : rows) {
            Project project = new Project();
            project.setId((Long) (row.get("PROJECT_ID")));

        }
        return null;
    }
}

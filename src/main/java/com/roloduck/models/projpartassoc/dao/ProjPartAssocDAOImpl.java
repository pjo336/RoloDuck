package com.roloduck.models.projpartassoc.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.models.projpartassoc.ProjPartAssoc;
import com.roloduck.utils.SQLUtils;
import com.roloduck.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public List<Long> findPartnersByProjectId(long projectId) {
        ProjPartAssoc assoc = new ProjPartAssoc();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(assoc.getAllColumnNames()) + " FROM " +
                assoc.getTableName() + " where project_id = ?";
        SQLUtils.printSQL(SQL);
        List<Long> partnerIds = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL, new Object[]{projectId});
        for (Map row : rows) {
            partnerIds.add((Long) row.get("PARTNER_ID"));
        }

        return partnerIds;
    }

    @Override
    public void removeAssoc(ProjPartAssoc assoc) {
        super.remove(assoc);
    }

    @Override
    public long count() {
        return super.count(new ProjPartAssoc());
    }
}

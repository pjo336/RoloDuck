package com.roloduck.models.projpartassoc.dao;

import com.roloduck.entity.dao.RoloDuckEntityDAOImpl;
import com.roloduck.exception.DAOException;
import com.roloduck.models.projpartassoc.ProjPartAssoc;
import com.roloduck.models.projpartassoc.ProjPartAssocMapper;
import com.roloduck.utils.SQLUtils;
import com.roloduck.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public ProjPartAssoc findAssoc(long projectId, long partnerId) throws DAOException {
        ProjPartAssoc assoc = new ProjPartAssoc();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(assoc.getAllColumnNames()) + " FROM " +
                assoc.getTableName() + " where project_id = ? AND partner_id = ?";
        SQLUtils.printSQL(SQL);
        try {
            List<ProjPartAssoc> associations = jdbcTemplate.query(SQL, new Object[]{projectId, partnerId},
                    new ProjPartAssocMapper());
            if(associations.size() > 1) {
                throw new DAOException("There are duplicate associations in existence, please fix.");
            } else if(associations.size() == 1) {
                return associations.get(0);
            } else {
                return null;
            }
        } catch (DataAccessException dae) {
            throw new DAOException("There was an access exception while finding the proj part assoc.");
        }
    }

    @Override
    public List<Long> findPartnersByProjectId(long projectId) throws DAOException {
        ProjPartAssoc assoc = new ProjPartAssoc();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(assoc.getAllColumnNames()) + " FROM " +
                assoc.getTableName() + " where project_id = ?";
        SQLUtils.printSQL(SQL);
        List<Long> partnerIds = new ArrayList<>();
        try {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL, projectId);
            for (Map row : rows) {
                partnerIds.add((Long) row.get("PARTNER_ID"));
            }
        } catch(DataAccessException DAE) {
            throw new DAOException("There was a problem finding the partners by projects.");
        }

        return partnerIds;
    }

    @Override
    public List<Long> findProjectsByPartnerId(long partnerId) throws DAOException {
        ProjPartAssoc assoc = new ProjPartAssoc();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(assoc.getAllColumnNames()) + " FROM " +
                assoc.getTableName() + " where partner_id = ?";
        SQLUtils.printSQL(SQL);
        List<Long> projectIds = new ArrayList<>();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL, partnerId);
            for (Map row : rows) {
                projectIds.add((Long) row.get("PROJECT_ID"));
            }
        } catch(DataAccessException DAE) {
            throw new DAOException("There was a problem finding the partners by projects.");
        }

        return projectIds;
    }

    @Override
    public boolean validateUniqueProjectPartnerConstraint(ProjPartAssoc association) throws DAOException {
        ProjPartAssoc assoc = new ProjPartAssoc();
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(assoc.getAllColumnNames()) + " FROM " +
                assoc.getTableName() + " WHERE partner_id = ? AND project_id = ?";
        SQLUtils.printSQL(SQL);
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL, association.getPartnerId(),
                    association.getProjectId());
            if(rows.size() > 0) {
                return false;
            }
        } catch(DataAccessException DAE) {
            throw new DAOException("There was a Data Access Exception while validating uniqueness of the association.");
        }
        return true;
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

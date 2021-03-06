package com.roloduck.entity.dao;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.DAOException;
import com.roloduck.utils.SQLUtils;
import com.roloduck.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

@Repository
public class RoloDuckEntityDAOImpl <E extends RoloDuckEntity> implements RoloDuckEntityDAO<E> {

    static final Logger logger = LoggerFactory.getLogger(RoloDuckEntityDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void insert(E entity){
        // Retrieve the table name, and names of all the applicable columns
        if(entity != null) {
            final String table = entity.getTableName();
            final String[] cols = entity.getDistinctColumnNames();
            // Build the column and ? strings needed for the insert query
            String columnPortion = generateColumnNamesString(cols);
            String questionMarks = generatePlaceholderString(cols);
            // Build the SQL query
            final String SQL = "INSERT INTO " + table + columnPortion + "VALUES" + questionMarks;
            SQLUtils.printSQL(SQL);
            // This keyholder will be filled with the generated id
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplateObject.update(entity.getPreparedStatementCreator(SQL), keyHolder);
            // Set the objects id to the value that was generated
            entity.setId(keyHolder.getKey().longValue());
        } else {
            logger.warn("Insert was called with a null entity.");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E restoreById(Object id, E entity) throws DAOException {
        if(id != null && entity != null) {
            final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(entity.getAllColumnNames()) + " FROM " +
                    entity.getTableName() + " WHERE id = ?";
            SQLUtils.printSQL(SQL);
            try {

                return (E) jdbcTemplateObject.queryForObject(SQL,
                        new Object[]{id}, entity.getEntityMapper());
            } catch(EmptyResultDataAccessException e) {
                // When we add the class name to the exception, we only want the name of the class, not the path to it
                String fullClassPathName = entity.getClass().getName();
                int period = fullClassPathName.lastIndexOf(".") + 1;
                String className = fullClassPathName.substring(period);
                throw new DAOException(className + " with ID: " + id + " was not found.", e);
            }
        } else {
            logger.warn("RestoreById was called with a null id or null entity.");
            throw new DAOException("RestoreById was called with invalid data.");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> find(E entity) {
        if(entity != null) {
            final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(entity.getAllColumnNames()) + " FROM " +
                    entity.getTableName();
            SQLUtils.printSQL(SQL);
            return jdbcTemplateObject.query(SQL, entity.getEntityMapper());
        } else {
            logger.warn("Find was called on a null entity.");
            return null;
        }
    }

    @Override
    public void remove(E entity) {
        if(entity != null) {
            String SQL = "DELETE FROM " + entity.getTableName() + " WHERE id = ?";
            SQLUtils.printSQL(SQL);
            jdbcTemplateObject.update(SQL, entity.getId());
        } else {
            logger.warn("Remove was called on a null entity.");
        }
    }

    @Override
    public Long count(E entity) {
        if(entity != null) {
            // TODO is this safe to print id?
            String SQL = "SELECT COUNT(id) FROM " + entity.getTableName();
            SQLUtils.printSQL(SQL);
            return jdbcTemplateObject.queryForObject(SQL, Long.class);
        } else {
            logger.warn("Count was called on a null entity");
            // TODO return 0?
            return 0L;
        }
    }

    /**
     * Build a string involving the columns in the entity's table
     * @param cols the array of column names retrieved from the entity
     * @return the final string of the column names
     */
    private String generateColumnNamesString(String[] cols) {
        StringBuilder colBuilder =  new StringBuilder();
        colBuilder.append(" (");
        int colAmount = cols.length;
        for(int i = 0; i < colAmount; i++) {
            colBuilder.append(cols[i]);
            if(i < colAmount - 1) {
                colBuilder.append(", ");
            }
        }
        colBuilder.append(") ");
        return colBuilder.toString();
    }

    /**
     * Build a string involving the applicable amount of ?
     * @param cols the array of column names retrieved from the entity
     * @return the final string of the applicable ?
     */
    private String generatePlaceholderString(String[] cols) {
        StringBuilder placeholderBuilder =  new StringBuilder();
        placeholderBuilder.append(" (");
        int colAmount = cols.length;
        for(int i = 0; i < colAmount; i++) {
            placeholderBuilder.append("?");
            if(i < colAmount - 1) {
                placeholderBuilder.append(", ");
            }
        }
        placeholderBuilder.append(") ");
        return placeholderBuilder.toString();
    }
}

package com.roloduck.entity.dao;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.DAOException;
import com.roloduck.utils.StringUtils;
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

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void insert(E entity) {
        // Retrieve the table name, and names of all the applicable columns
        final String table = entity.getTableName();
        final String[] cols = entity.getDistinctColumnNames();
        // Build the column and ? strings needed for the insert query
        String columnPortion = generateColumnNamesString(cols);
        String questionMarks = generatePlaceholderString(cols);
        // Build the SQL query
        final String SQL = "INSERT INTO " + table + columnPortion + "VALUES" + questionMarks;
        // This keyholder will be filled with the generated id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(entity.getPreparedStatementCreator(SQL), keyHolder);
        // Set the objects id to the value that was generated
        entity.setId(keyHolder.getKey().longValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public E restoreById(Object id, E entity) throws DAOException {
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(entity.getAllColumnNames()) + " FROM " +
                entity.getTableName() + " WHERE id = ?";
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
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> find(E entity) {
        final String SQL = "SELECT " + StringUtils.convertStrArrToSQLColStr(entity.getAllColumnNames()) + " FROM " +
                entity.getTableName();
        return jdbcTemplateObject.query(SQL, entity.getEntityMapper());
    }

    @Override
    public void remove(E entity) {
        String SQL = "DELETE FROM " + entity.getTableName() + " WHERE id = ?";
        jdbcTemplateObject.update(SQL, entity.getId());
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

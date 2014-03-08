package com.roloduck.entity.dao;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
        final String[] cols = entity.getColumnNames();
        // Build the column and ? strings needed for the insert query
        String columnPortion = generateColumnNamesString(cols);
        String questionMarks = generatePlaceholderString(cols);
        // Build the SQL query
        final String SQL = "insert into " + table + columnPortion + "values" + questionMarks;
        // This keyholder will be filled with the generated id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(entity.getPreparedStatementCreator(SQL), keyHolder);
        // Set the objects id to the value that was generated
        entity.setId(keyHolder.getKey().longValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public E restoreById(Object id, E entity) throws NotFoundException {
        final String SQL = "SELECT * FROM " + entity.getTableName() + " WHERE id = ?";
        E ret = (E) jdbcTemplateObject.queryForObject(SQL,
                new Object[]{id}, entity.getEntityMapper());
        if(ret != null) {
            return ret;
        } else {
            throw new NotFoundException("No " + entity.getClass().getName() + " with " + id + " id found");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll(E entity) {
        final String SQL = "SELECT * FROM " + entity.getTableName();
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

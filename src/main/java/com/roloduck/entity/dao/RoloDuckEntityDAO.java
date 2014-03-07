package com.roloduck.entity.dao;

import com.roloduck.entity.RoloDuckEntity;
import com.roloduck.exception.NotFoundException;

import java.util.List;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/7/14
 * RoloDuck
 */

public interface RoloDuckEntityDAO<E extends RoloDuckEntity> {

    /**
     * Create a Prepared Statment from the PicklePaymentEntity and insert the object into the database
     * @param entity a PicklePaymentsEntity object to be inserted
     */
    public void insert(E entity);

    /**
     * Find the entity based on the given id. Throw exception if not found.
     * @param id the id used to search
     * @param entity the entity type being searched for. Used to select the correct rowmapper
     * @return the entity found in the database with the matching id
     * @exception com.roloduck.exception.NotFoundException
     */
    public E restoreById(Object id, E entity) throws NotFoundException;

    /**
     * Return a list of all of the given entities in the database
     * @return list of entities
     */
    public List<E> findAll(E entity);

    /**
     * Remove the given entity
     * @param entity the entity to be removed
     */
    public void remove(E entity);
}

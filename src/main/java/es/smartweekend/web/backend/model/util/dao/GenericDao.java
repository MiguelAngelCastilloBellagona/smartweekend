package es.smartweekend.web.backend.model.util.dao;

import java.io.Serializable;

import es.smartweekend.web.backend.model.util.exceptions.InstanceException;

/**
 * @author Daniel Gómez Silva
 */
public interface GenericDao<E, PK extends Serializable> {

	void save(E entity);

	E find(PK id) throws InstanceException;

	boolean exists(PK id);

	void remove(PK id) throws InstanceException;

}

package es.smartweekend.web.backend.model.util.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.model.util.exceptions.InstanceNotFoundException;

import java.lang.reflect.ParameterizedType;

/**
 * @author Daniel Gómez Silva
 */
public class GenericDaoHibernate<E, PK extends Serializable> implements GenericDao<E, PK> {

	private SessionFactory sessionFactory;

	private Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDaoHibernate() {
		this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(E entity) {
		getSession().saveOrUpdate(entity);
	}

	public boolean exists(PK id) {
		return getSession().createCriteria(entityClass).add(Restrictions.idEq(id)).setProjection(Projections.id()).uniqueResult() != null;
	}

	@SuppressWarnings("unchecked")
	public E find(PK id) throws InstanceNotFoundException {
		E entity = (E) getSession().get(entityClass, (Serializable) id);
		if (entity == null) {
			throw new InstanceNotFoundException(id, entityClass.getName());
		}
		return entity;
	}

	public void remove(PK id) throws InstanceNotFoundException {
		getSession().delete(find(id));
	}

}

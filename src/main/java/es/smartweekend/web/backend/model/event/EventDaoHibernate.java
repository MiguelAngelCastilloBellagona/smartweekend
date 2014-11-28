/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.smartweekend.web.backend.model.event;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import es.smartweekend.web.backend.model.util.dao.GenericDaoHibernate;

/**
 *  @author Miguel Ángel Castillo Bellagona
 */
@Repository("eventDao")
public class EventDaoHibernate extends GenericDaoHibernate<Event, Integer> implements EventDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Event> getAllEvents() {
		return getSession().createQuery( "SELECT e " +
				 "FROM Event e ").list();
	}
	
	@Override
    public Event findEventByName(String name) {
        return (Event) getSession().createCriteria(Event.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
    }
}

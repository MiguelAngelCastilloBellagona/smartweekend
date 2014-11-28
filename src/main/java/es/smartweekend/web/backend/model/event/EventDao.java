/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.smartweekend.web.backend.model.event;

import java.util.List;

import es.smartweekend.web.backend.model.util.dao.GenericDao;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public interface EventDao extends GenericDao<Event, Integer> {
	
	public List<Event> getAllEvents();
    
    public Event findEventByName(String name);

}

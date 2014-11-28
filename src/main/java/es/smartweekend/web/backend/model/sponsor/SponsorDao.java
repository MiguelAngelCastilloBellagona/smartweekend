package es.smartweekend.web.backend.model.sponsor;

import java.util.List;

import es.smartweekend.web.backend.model.util.dao.GenericDao;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface SponsorDao extends GenericDao<Sponsor,Integer> {
	
	public List<Sponsor> getAll();
	
	public List<Sponsor> getAllByEvent(int eventId);

}

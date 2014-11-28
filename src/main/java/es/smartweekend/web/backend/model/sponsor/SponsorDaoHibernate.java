package es.smartweekend.web.backend.model.sponsor;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.smartweekend.web.backend.model.util.dao.GenericDaoHibernate;


/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("sponsorDao")
public class SponsorDaoHibernate extends GenericDaoHibernate<Sponsor,Integer> implements SponsorDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Sponsor> getAll() {
		return getSession().createQuery(
	        	"SELECT s " +
		        "FROM Sponsor s ").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sponsor> getAllByEvent(int eventId) {
		return getSession().createQuery(
	        	"SELECT s " +
		        "FROM Sponsor s " +
		        "WHERE s.event.eventId = :eventId"
	        	).setParameter("eventId", eventId).list();
	}

}

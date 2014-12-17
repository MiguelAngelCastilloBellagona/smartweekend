package es.smartweekend.web.backend.model.sponsor;

import java.util.List;

import org.hibernate.Query;
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
	public List<Sponsor> getAll(int startindex, int maxResults, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT s " +
		        "FROM Sponsor s " +
	        	"ORDER BY s." + orderBy + aux);
		if(maxResults<1) return query.list();
		else return query.setFirstResult(startindex).setMaxResults(maxResults).list();
	}
	
	@Override
	public long getAllTAM() {
		return (long) getSession().createQuery(
	        	"SELECT count(s) " +
		        "FROM Sponsor s "
		        ).uniqueResult();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sponsor> getAllByEvent(int eventId, int startindex, int maxResults, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT s " +
		        "FROM Sponsor s " +
		        "WHERE s.event.eventId = :eventId" +
	        	"ORDER BY s." + orderBy + aux).setParameter("eventId", eventId);
		if(maxResults<1) return query.list();
		else return query.setFirstResult(startindex).setMaxResults(maxResults).list();
	}
	
	@Override
	public long getAllByEventTAM(int eventId) {
		return (long) getSession().createQuery(
	        	"SELECT count(s) " +
		        "FROM Sponsor s " +
		        "WHERE s.event.eventId = :eventId"
		        ).setParameter("eventId", eventId).uniqueResult();	
	}

}

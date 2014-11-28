package es.smartweekend.web.backend.model.newsItem;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.smartweekend.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Repository("NewsItemDao")
public class NewsItemDaoHibernate extends GenericDaoHibernate<NewsItem, Integer> implements NewsItemDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsItem> getAllNewsItemByEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT n " +
		        "FROM NewsItem n " +
	        	"WHERE n.event.eventId = :eventId " +
	        	"ORDER BY n." + orderBy + aux
		        ).setParameter("eventId", eventId);
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}
	
	@Override
	public long getAllNewsItemByEventTAM(int eventId) {
		return (long) getSession().createQuery(
	        	"SELECT count(n) " + 
	        	"WHERE n.event.eventId = :eventId " +
	        	"FROM NewsItem n" 
		        ).setParameter("eventId", eventId).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsItem> getPublishedAllNewsItemByEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT n " +
		        "FROM NewsItem n " +
	        	"WHERE AND n.publishDate <= :now " +
	        	"AND n.event.eventId = :eventId " +
	        	"ORDER BY n." + orderBy + aux
		        ).setParameter("eventId", eventId).setParameter("now", now);
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}
	
	@Override
	public long getPublishedNewsItemByEventTAM(int eventId) {
		return (long) getSession().createQuery(
	        	"SELECT count(n) " + 
	        	"FROM NewsItem n " +
	        	"WHERE n.publishDate <= :now " +
	        	"AND n.event.eventId = :eventId "
		        ).setParameter("eventId", eventId).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsItem> getAllNewsItemFromUserByEvent(int eventId, int userId, int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT n " +
		        "FROM NewsItem n " +
	        	"WHERE n.publisher.userId = :userId " +
	        	"AND n.event.eventId = :eventId " +
	        	"ORDER BY n." + orderBy + aux
	        	).setParameter("eventId", eventId).setParameter("userId", userId);
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}
	
	@Override
	public long getAllNewsItemFromUserByEventTAM(int eventId, int userId) {
		return (long) getSession().createQuery(
	        	"SELECT count(n) " + 
	        	"FROM NewsItem n " +
	        	"WHERE n.publisher.userId = :userId " +
	        	"AND n.event.eventId = :eventId "
		        ).setParameter("eventId", eventId).uniqueResult();
	}
}

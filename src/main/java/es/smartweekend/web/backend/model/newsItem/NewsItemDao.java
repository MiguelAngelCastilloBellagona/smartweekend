package es.smartweekend.web.backend.model.newsItem;

import java.util.List;

import es.smartweekend.web.backend.model.util.dao.GenericDao;


/**
 * @author Miguel Ángel Castillo Bellagona
 */
public interface NewsItemDao extends GenericDao<NewsItem,Integer> {
	
	public List<NewsItem> getAllNewsItemByEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc);
	
	public long getAllNewsItemByEventTAM(int eventId);
	
	public List<NewsItem> getPublishedAllNewsItemByEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc);
	
	public long getPublishedNewsItemByEventTAM(int eventId);
	
	public List<NewsItem> getAllNewsItemFromUserByEvent(int eventId, int userId, int startIndex, int cont, String orderBy, boolean desc);
	
	public long getAllNewsItemFromUserByEventTAM(int eventId, int userId);
	
}

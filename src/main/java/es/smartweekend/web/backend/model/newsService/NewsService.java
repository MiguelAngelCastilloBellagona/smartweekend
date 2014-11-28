package es.smartweekend.web.backend.model.newsService;

import java.util.List;

import es.smartweekend.web.backend.model.newsItem.NewsItem;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

public interface NewsService {

	//ANONYMOUS
	
	public List<NewsItem> getPublishedNewsItemFromEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
	
	public long getPublishedNewsItemTamFromEvent(int eventId) throws ServiceException;
	
	//USER
	
	
	//ADMIN
	
	public NewsItem addNewsADMIN(String sessionId, NewsItem newsItem) throws ServiceException;
	
	public NewsItem changeNewsDataADMIN(String sessionId, int newsItemId, NewsItem newsData) throws ServiceException;
	
    public NewsItem getNewsItemADMIN(String sessionId, int newsItemId) throws ServiceException;
    
    public List<NewsItem> getAllNewsItemADMINFromEvent(String sessionId, int eventId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
    
    public long getAllNewsItemTamADMINFromEvent(String sessionId, int eventId) throws ServiceException;
    
    public void removeNewsADMIN(String sessionId, int newsItemId) throws ServiceException;
	
}

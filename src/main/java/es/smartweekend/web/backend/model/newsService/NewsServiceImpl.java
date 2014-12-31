package es.smartweekend.web.backend.model.newsService;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.event.EventDao;
import es.smartweekend.web.backend.model.newsItem.NewsItem;
import es.smartweekend.web.backend.model.newsItem.NewsItemDao;
import es.smartweekend.web.backend.model.user.User;
import es.smartweekend.web.backend.model.user.UserDao;
import es.smartweekend.web.backend.model.util.exceptions.InstanceException;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("NewsService")
@Transactional
public class NewsServiceImpl implements NewsService {
	
	private static final String SPONSORVICEPERMISIONLEVEL = "S";
	
	@Autowired
	private NewsItemDao newsItemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	EventDao eventDao;
	
	@Transactional(readOnly=true)
	public boolean checkPermissions(User user, String permisionLevelRequired) {
		try {
			return userDao.find(user.getUserId()).getPremissions().contains(permisionLevelRequired);
		} catch (InstanceException e) {
			return false;
		}
	}

	// ANONYMOUS

	@Override
	@Transactional(readOnly=true)
	public List<NewsItem> getPublishedNewsItemFromEvent(int eventId,
			int startIndex, int cont, String orderBy, boolean desc)
			throws ServiceException {
		return newsItemDao.getPublishedAllNewsItemByEvent(eventId, startIndex, cont, orderBy, desc);
	}

	@Override
	@Transactional(readOnly=true)
	public long getPublishedNewsItemTamFromEvent(int eventId)
			throws ServiceException {
		return newsItemDao.getAllNewsItemByEventTAM(eventId);
	}

	// USER

	// ADMIN

	@Override
	@Transactional
	public NewsItem addNewsADMIN(String sessionId, int eventId, NewsItem newsItem)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			
			newsItem.setEvent(event);
			if(newsItem.getEvent()==null) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			if(newsItem.getTitle()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"title");
			if(newsItem.getContent()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"Content");

			newsItem.setPublisher(userDao.find(SessionManager.getSession(sessionId).getUserId()));
			newsItem.setCreationDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
			if(newsItem.getPublishDate()==null) newsItem.setPublishDate(newsItem.getCreationDate());
			newsItemDao.save(newsItem);
			return newsItem;
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
	}

	@Override
	@Transactional
	public NewsItem changeNewsDataADMIN(String sessionId, int newsItemId,
			NewsItem newsData) throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			NewsItem news = newsItemDao.find(newsItemId);
			newsData.setNewsItemId(newsItemId);
			if(newsData.getTitle()!=null) news.setTitle(newsData.getTitle());
			if(newsData.getPublishDate()!=null) news.setPublishDate(newsData.getPublishDate());
			if(newsData.getContent()!=null) news.setContent(newsData.getContent());
			newsItemDao.save(news);
			return news;
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"NewsItem");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public NewsItem getNewsItemADMIN(String sessionId, int newsItemId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			return newsItemDao.find(newsItemId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"newsItem");
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<NewsItem> getAllNewsItemADMINFromEvent(String sessionId,
			int eventId, int startIndex, int cont, String orderBy, boolean desc)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		return newsItemDao.getAllNewsItemByEvent(eventId, startIndex, cont, orderBy, desc);
	}

	@Override
	@Transactional(readOnly=true)
	public long getAllNewsItemTamADMINFromEvent(String sessionId, int eventId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		return newsItemDao.getAllNewsItemByEventTAM(eventId);
	}

	@Override
	@Transactional
	public void removeNewsADMIN(String sessionId, int newsItemId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			newsItemDao.remove(newsItemId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"newsItem");
		}
	}

}

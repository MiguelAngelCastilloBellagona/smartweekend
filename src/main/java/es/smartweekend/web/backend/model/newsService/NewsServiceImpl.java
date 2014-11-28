package es.smartweekend.web.backend.model.newsService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.smartweekend.web.backend.model.newsItem.NewsItem;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("NewsService")
@Transactional
public class NewsServiceImpl implements NewsService {

	// ANONYMOUS

	@Override
	public List<NewsItem> getPublishedNewsItemFromEvent(int eventId,
			int startIndex, int cont, String orderBy, boolean desc)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getPublishedNewsItemTamFromEvent(int eventId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	// USER

	// ADMIN

	@Override
	public NewsItem addNewsADMIN(String sessionId, NewsItem newsItem)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NewsItem changeNewsDataADMIN(String sessionId, int newsItemId,
			NewsItem newsData) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NewsItem getNewsItemADMIN(String sessionId, int newsItemId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NewsItem> getAllNewsItemADMINFromEvent(String sessionId,
			int eventId, int startIndex, int cont, String orderBy, boolean desc)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getAllNewsItemTamADMINFromEvent(String sessionId, int eventId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeNewsADMIN(String sessionId, int newsItemId)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

}

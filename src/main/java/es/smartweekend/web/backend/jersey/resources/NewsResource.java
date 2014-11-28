package es.smartweekend.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.model.newsItem.NewsItem;
import es.smartweekend.web.backend.model.newsService.NewsService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class NewsResource {

	@Autowired
	private NewsService newsService;
	
	//ANONYMOUS
	
	@Path("/getPublishedNews/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<NewsItem> getPublishedNewsItem(
			@PathParam("eventId")                                int eventId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("publishDate") @QueryParam("orderBy")  String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc
			) throws ServiceException {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return newsService.getPublishedNewsItemFromEvent(eventId,startIndex,cont,orderBy,b);
	}
	
	@Path("/getPublishedNewsTAM/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public long getPublishedNewsItemTam(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return newsService.getPublishedNewsItemTamFromEvent(eventId);
	}

	//USER
	
	
	//ADMIN
	
	@Path("/admin")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public NewsItem NewsADMIN(@HeaderParam("sessionId") String sessionId, NewsItem newsItem) throws ServiceException {
		return newsService.addNewsADMIN(sessionId, newsItem);
	}
	
	@Path("/admin/{newsItemId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public NewsItem changeNewsDataADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId, NewsItem newsData) throws ServiceException {
		return newsService.changeNewsDataADMIN(sessionId, newsItemId, newsData);
	}
	
	@Path("/admin/{newsItemId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public NewsItem getNewsItemADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) throws ServiceException {
		return newsService.getNewsItemADMIN(sessionId, newsItemId);
	}

	
	@Path("/admin/getAllNews/{eventId}/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<NewsItem> getAllNewsItemADMIN(@HeaderParam("sessionId") String sessionId,
			@PathParam("eventId")                                int eventId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("publishDate") @QueryParam("orderBy")  String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc
			) throws ServiceException {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return newsService.getAllNewsItemADMINFromEvent(sessionId,eventId,startIndex,cont,orderBy,b);
	}
    
	@Path("/admin/getAllNewsTAM/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public long getAllNewsItemTamADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) throws ServiceException {
		return newsService.getAllNewsItemTamADMINFromEvent(sessionId,eventId);
	}

	@Path("/admin/removeNewsItem/{newsItemId}")
	@DELETE
    public void removeNewsADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) throws ServiceException {
		newsService.removeNewsADMIN(sessionId, newsItemId);
	}
	
}

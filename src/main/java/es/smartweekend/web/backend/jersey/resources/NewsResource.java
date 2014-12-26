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
import javax.ws.rs.core.Response;

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
	public Response getPublishedNewsItem(
			@PathParam("eventId")                                int eventId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("publishDate") @QueryParam("orderBy")  String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc ) {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		try {
			List<NewsItem> l =  newsService.getPublishedNewsItemFromEvent(eventId,startIndex,cont,orderBy,b);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/getPublishedNewsTAM/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPublishedNewsItemTam(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) {
		try {
			long l = newsService.getPublishedNewsItemTamFromEvent(eventId);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	//USER
	
	
	//ADMIN
	
	@Path("/admin")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response NewsADMIN(@HeaderParam("sessionId") String sessionId, NewsItem newsItem) {
		try {
			NewsItem n = newsService.addNewsADMIN(sessionId, newsItem);
			return Response.status(200).entity(n).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/{newsItemId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response changeNewsDataADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId, NewsItem newsData) {
		try {
			NewsItem n = newsService.changeNewsDataADMIN(sessionId, newsItemId, newsData);
			return Response.status(200).entity(n).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/{newsItemId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getNewsItemADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) {
		try {
			NewsItem n =  newsService.getNewsItemADMIN(sessionId, newsItemId);
			return Response.status(200).entity(n).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	
	@Path("/admin/getAllNews/{eventId}/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllNewsItemADMIN(@HeaderParam("sessionId") String sessionId,
			@PathParam("eventId")                                int eventId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("publishDate") @QueryParam("orderBy")  String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc ) {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		try {
			List<NewsItem> l = newsService.getAllNewsItemADMINFromEvent(sessionId,eventId,startIndex,cont,orderBy,b);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
    
	@Path("/admin/getAllNewsTAM/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllNewsItemTamADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) {
		try {
			long l = newsService.getAllNewsItemTamADMINFromEvent(sessionId,eventId);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	@Path("/admin/removeNewsItem/{newsItemId}")
	@DELETE
    public Response removeNewsADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) {
		try {
			newsService.removeNewsADMIN(sessionId, newsItemId);
			return Response.status(203).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
}

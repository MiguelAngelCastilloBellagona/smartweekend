package es.smartweekend.web.backend.jersey.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.Request;
import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.jersey.util.ApplicationContextProvider;
import es.smartweekend.web.backend.model.newsItem.NewsItem;
import es.smartweekend.web.backend.model.newsService.NewsService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("news")
public class NewsResource {

	private String[] s = {"newsItemId","title","creationDate","publishDate","content","publisher","publisher.login","event"};
	private ArrayList<String> l;
	
	@Autowired
	private NewsService newsService;
	
	public NewsResource() {
		this.newsService  = ApplicationContextProvider.getApplicationContext().getBean(NewsService.class);
		l = new ArrayList<String>();
		l.add(s[0]);l.add(s[1]);l.add(s[2]);l.add(s[3]);l.add(s[4]);l.add(s[5]);l.add(s[6]);l.add(s[7]);
	}
	
	
	//ANONYMOUS
	
	@Path("/getPublishedNews/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPublishedNewsItem(
			@Context Request request,
			@PathParam("eventId")                                int eventId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("publishDate") @QueryParam("orderBy")  String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc ) {
		if(request!=null) {
			String ip = request.getRemoteAddr();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
			Calendar now = Calendar.getInstance();
			System.out.println("[getPublishedNewsItem]\t from: [" + ip + "]\t at: [" + dateFormat.format(now.getTime()) + "]");
		}
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		try {
			if(l.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
			List<NewsItem> l =  newsService.getPublishedNewsItemFromEvent(eventId,startIndex,cont,orderBy,b);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/getPublishedNewsTAM/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPublishedNewsItemTam(@Context Request request, @PathParam("eventId") int eventId) {
		if(request!=null) {
			String ip = request.getRemoteAddr();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
			Calendar now = Calendar.getInstance();
			System.out.println("[getPublishedNewsItemTam]\t from: [" + ip + "]\t at: [" + dateFormat.format(now.getTime()) + "]");
		}

		try {
			long l = newsService.getPublishedNewsItemTamFromEvent(eventId);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
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
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			NewsItem n = newsService.addNewsADMIN(sessionId, newsItem);
			return Response.status(200).entity(n).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/{newsItemId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response changeNewsDataADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId, NewsItem newsData) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			NewsItem n = newsService.changeNewsDataADMIN(sessionId, newsItemId, newsData);
			return Response.status(200).entity(n).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/{newsItemId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getNewsItemADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			NewsItem n =  newsService.getNewsItemADMIN(sessionId, newsItemId);
			return Response.status(200).entity(n).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
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
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			if(l.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
			List<NewsItem> l = newsService.getAllNewsItemADMINFromEvent(sessionId,eventId,startIndex,cont,orderBy,b);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
    
	@Path("/admin/getAllNewsTAM/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllNewsItemTamADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			long l = newsService.getAllNewsItemTamADMINFromEvent(sessionId,eventId);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	@Path("/admin/removeNewsItem/{newsItemId}")
	@DELETE
    public Response removeNewsADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			newsService.removeNewsADMIN(sessionId, newsItemId);
			return Response.status(204).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
}

package es.smartweekend.web.backend.jersey.resources;

import java.util.ArrayList;
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
import es.smartweekend.web.backend.jersey.util.RequestControl;
import es.smartweekend.web.backend.model.newsItem.NewsItem;
import es.smartweekend.web.backend.model.newsService.NewsService;
import es.smartweekend.web.backend.model.userService.UserService;
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
	
	@Autowired
    private UserService userService;
	
	public NewsResource() {
		this.newsService  = ApplicationContextProvider.getApplicationContext().getBean(NewsService.class);
		this.userService  = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
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
		RequestControl.showContextData("getPublishedNewsItem",request);
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		try {
			if(l.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
			List<NewsItem> l =  newsService.getPublishedNewsItemFromEvent(eventId,startIndex,cont,orderBy,b);
			if(request!=null) System.out.println();
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
		RequestControl.showContextData("getPublishedNewsItemTam",request);
		try {
			long l = newsService.getPublishedNewsItemTamFromEvent(eventId);
			if(request!=null) System.out.println();
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
	public Response addNewsADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, NewsItem newsItem) {
		RequestControl.showContextData("addNewsADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			NewsItem n = newsService.addNewsADMIN(sessionId, newsItem);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "title {" + n.getTitle() + "}"); 
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
	public Response changeNewsDataADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId, NewsItem newsData) {
		RequestControl.showContextData("changeNewsDataADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			NewsItem n = newsService.changeNewsDataADMIN(sessionId, newsItemId, newsData);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "title {" + n.getTitle() + "}"); 
			return Response.status(200).entity(n).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/{newsItemId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getNewsItemADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) {
		RequestControl.showContextData("getNewsItemADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			NewsItem n =  newsService.getNewsItemADMIN(sessionId, newsItemId);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "title {" + n.getTitle() + "}");
			return Response.status(200).entity(n).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	
	@Path("/admin/getAllNews/{eventId}/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllNewsItemADMIN(@Context Request request,
			@HeaderParam("sessionId") String sessionId,
			@PathParam("eventId")                                int eventId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("publishDate") @QueryParam("orderBy")  String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc ) {
		RequestControl.showContextData("getAllNewsItemADMIN",request);
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			if(l.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
			List<NewsItem> l = newsService.getAllNewsItemADMINFromEvent(sessionId,eventId,startIndex,cont,orderBy,b);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}");
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
    
	@Path("/admin/getAllNewsTAM/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllNewsItemTamADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) {
		RequestControl.showContextData("getAllNewsItemTamADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			long l = newsService.getAllNewsItemTamADMINFromEvent(sessionId,eventId);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}");
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	@Path("/admin/removeNewsItem/{newsItemId}")
	@DELETE
    public Response removeNewsADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("newsItemId") int newsItemId) {
		RequestControl.showContextData("removeNewsADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			String title = newsService.getNewsItemADMIN(sessionId, newsItemId).getTitle();
			newsService.removeNewsADMIN(sessionId, newsItemId);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "title {" + title + "}");
			return Response.status(204).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
}

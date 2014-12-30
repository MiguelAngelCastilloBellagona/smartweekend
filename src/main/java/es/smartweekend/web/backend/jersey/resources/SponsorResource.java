package es.smartweekend.web.backend.jersey.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import es.smartweekend.web.backend.model.sponsor.Sponsor;
import es.smartweekend.web.backend.model.sponsorService.SponsorService;
import es.smartweekend.web.backend.model.userService.UserService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("sponsor")
public class SponsorResource {

	private String[] s = {"sponsorId","name","url","imageurl","event"};
	private ArrayList<String> l;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private UserService userService;
	
	public SponsorResource(){
		this.sponsorService  = ApplicationContextProvider.getApplicationContext().getBean(SponsorService.class);
		this.userService  = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
		l = new ArrayList<String>();
		l.add(s[0]);l.add(s[1]);l.add(s[2]);l.add(s[3]);l.add(s[4]);
	}
	
	//ANONYMOUS
	
	@Path("/getAllEventSponsor/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllEventSponsor(@Context Request request, @PathParam("eventId") int eventId) {
		RequestControl.showContextData("getAllEventSponsor",request);
		try {
			List<Sponsor> lista = sponsorService.getAllEventSponsor(eventId);
			if(request!=null) System.out.println("eventid {" + Integer.toString(eventId) + "}");
			return Response.status(200).entity(lista).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	//USER
	
	//ADMIN
	
	@Path("/admin/getAllSponsor")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllSponsorADMIN(@Context Request request,
			@HeaderParam("sessionId") String sessionId,
			@DefaultValue("1") @QueryParam("page") int page, 
			@DefaultValue("0") @QueryParam("pageTam") int pageTam,
			@DefaultValue("sponsorId") @QueryParam("orderBy") String orderBy,
			@DefaultValue("1") @QueryParam("desc") int desc){
		RequestControl.showContextData("getAllSponsorADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			if(l.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
			int startIndex = page*pageTam - pageTam;
			int cont = pageTam;
			boolean b = true;
			if(desc==0) b = false;
			List<Sponsor> lista = sponsorService.getAllSponsorADMIN(sessionId, startIndex, cont, orderBy, b);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}");
			return Response.status(200).entity(lista).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/getAllSponsorTAM")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllSponsorTAMADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId) {
		try {
			RequestControl.showContextData("getAllSponsorTAMADMIN",request);
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			long i = sponsorService.getAllSponsorTAMADMIN(sessionId);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}");
			return Response.status(200).entity(i).build();
		}
		catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/getSpsonsor/{sponsorId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getSponsorADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("sponsorId") int sponsorId) {
		RequestControl.showContextData("getSponsorADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Sponsor s = sponsorService.getSponsorADMIN(sessionId, sponsorId);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "sponsorId {" + Integer.toString(sponsorId) + "}");
			return Response.status(200).entity(s).build();
		}
		catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/addSpsonsorToEvent/{eventId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response addSponsorADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, Sponsor sponsor, @PathParam("eventId") int eventId) {
		RequestControl.showContextData("addSponsorADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Sponsor s = sponsorService.addSponsorToEventADMIN(sessionId, eventId, sponsor);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "sponsorId {" + Integer.toString(s.getSponsorId()) + "}\t" + "sponsorName {" + s.getName() + "}");
			return Response.status(200).entity(s).build();
		}
		catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
		
	}
	
	@Path("/admin/modyfySpsonsor/{sponsorId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response changeSponsorADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("sponsorId") int sponsorId, Sponsor sponsorData) throws ServiceException {
		RequestControl.showContextData("changeSponsorADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Sponsor s = sponsorService.changeSponsorADMIN(sessionId, sponsorId, sponsorData);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "sponsorId {" + Integer.toString(s.getSponsorId()) + "}\t" + "sponsorName {" + s.getName() + "}");
			return Response.status(200).entity(s).build();
		}
		catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/removeSpsonsor/{sponsorId}")
	@DELETE
	public Response removeSponsorADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, int sponsorId) throws ServiceException {
		RequestControl.showContextData("removeSponsorADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			sponsorService.removeSponsorADMIN(sessionId, sponsorId);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "sponsorId {" + sponsorId + "}");
			return Response.status(204 ).build();
		}
		catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
}

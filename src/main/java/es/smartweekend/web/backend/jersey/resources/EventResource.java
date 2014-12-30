package es.smartweekend.web.backend.jersey.resources;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.Request;
import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.jersey.util.ApplicationContextProvider;
import es.smartweekend.web.backend.jersey.util.RequestControl;
import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.event.Event.EventBasicData;
import es.smartweekend.web.backend.model.eventService.EventService;
import es.smartweekend.web.backend.model.userService.UserService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("event")
public class EventResource {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
	
	public EventResource() {
		this.eventService  = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
		this.userService  = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
	}
	
	//ANONYMOUS
	
	@Path("/{eventId}/{eventData}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEventData(@Context Request request, @PathParam("eventId") int eventId, @PathParam("eventData") String eventData) {
		RequestControl.showContextData("getEventData",request);
		try {
			Event e = eventService.getEvent(eventId);
			Object o = null;
			
			switch (eventData) {
				case "name" 		: o = e.getName(); break;
				case "minimunAge" 	: o =e.getMinimunAge(); break;
				case "startDate" 	: o = dateFormat.format(e.getStartDate().getTime()); break;
				case "endDate" 		: o = dateFormat.format(e.getEndDate().getTime()); break;
				case "isopen" 		: o = eventService.eventIsOpen(eventId); break;
				case "rules" 		: o = e.getNormas(); break;
				default				: throw new ServiceException(ServiceException.INCORRECT_FIELD,eventData);
			}
			if(request!=null) System.out.println();
			return Response.status(200).entity(o).build();
			
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllEventsName(@Context Request request) {
		RequestControl.showContextData("getAllEventsName",request);
		try {
			List<EventBasicData> l = (List<EventBasicData>) eventService.getAllEvents().stream().map(Event::getEventBasicData).collect(Collectors.toList());
			if(request!=null) System.out.println();
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	//USER
	
	
	//ADMIN
	
	@Path("/{eventId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEventADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) {
		RequestControl.showContextData("getEventADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Event e = eventService.getEventADMIN(sessionId, eventId);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "eventId {" + eventId + "}");
			return Response.status(200).entity(e).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response createEventADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, Event eventData) {
		RequestControl.showContextData("createEventADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Event e = eventService.createEventADMIN(sessionId, eventData);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "event name {" + e.getName() + "}");
			return Response.status(200).entity(e).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/{eventId}")
	@DELETE
	public Response deleteEventADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) {
		RequestControl.showContextData("deleteEventADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			eventService.removeEventADMIN(sessionId, eventId);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "eventId {" + eventId + "}");
			return Response.status(204).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/{eventId}")
	@PUT
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response changeEventDataADMIN(@Context Request request, @HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Event eventData) {
		RequestControl.showContextData("changeEventDataADMIN",request);
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Event e = eventService.changeEventDataADMIN(sessionId, eventId, eventData);
			String login = userService.getCurrenUserUSER(sessionId).getLogin();
			if(request!=null) System.out.println("login {" + login + "}\t" + "eventId {" + eventId + "}");
			return Response.status(200).entity(e).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
}

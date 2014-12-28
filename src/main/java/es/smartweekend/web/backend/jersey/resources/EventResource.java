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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.jersey.util.ApplicationContextProvider;
import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.event.Event.EventBasicData;
import es.smartweekend.web.backend.model.eventService.EventService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("event")
public class EventResource {
	
	@Autowired
	private EventService eventService;
	
	public EventResource() {
		this.eventService  = ApplicationContextProvider.getApplicationContext().getBean(EventService.class);
	}
	
	//ANONYMOUS
	
	@Path("/{eventId}/{eventData}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEventData(@PathParam("eventId") int eventId, @PathParam("eventData") String eventData) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
			
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
			
			return Response.status(200).entity(o).build();
			
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllEventsName() {
		try {
			List<EventBasicData> l = (List<EventBasicData>) eventService.getAllEvents().stream().map(Event::getEventBasicData).collect(Collectors.toList());
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
	public Response getEventADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Event e = eventService.getEventADMIN(sessionId, eventId);
			return Response.status(200).entity(e).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response createEventADMIN(@HeaderParam("sessionId") String sessionId, Event eventData) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Event e = eventService.createEventADMIN(sessionId, eventData);
			return Response.status(200).entity(e).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/{eventId}")
	@DELETE
	public Response deleteEventADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			eventService.removeEventADMIN(sessionId, eventId);
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
	public Response changeEventDataADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("eventId") int eventId, Event eventData) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			Event e = eventService.changeEventDataADMIN(sessionId, eventId, eventData);
			return Response.status(200).entity(e).build();
		} catch (ServiceException e) {
			System.out.println(e.toString());
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
}

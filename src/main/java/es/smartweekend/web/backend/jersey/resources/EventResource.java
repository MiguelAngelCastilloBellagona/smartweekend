package es.smartweekend.web.backend.jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.eventService.EventService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class EventResource {
	
	@Autowired
	private EventService eventService;
	
	@Path("/{eventId}/{eventData}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEventData(@PathParam("eventId") int eventId, @PathParam("eventData") String eventData) {
		try {
			Event e = eventService.getEvent(eventId);
			Object o = null;
			
			switch (eventData) {
				case "rules": 	o = e.getNormas(); break;
				case "isopen": 	break;
			}
			
			return Response.status(200).entity(o).build();
			
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
		
	}
	
	//ANONYMOUS
	
	
	//USER
	
	
	//ADMIN

}

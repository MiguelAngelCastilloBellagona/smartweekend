package es.smartweekend.web.backend.jersey.resources;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.event.Event.EventBasicData;
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
				case "name" 		: o = e.getName(); break;
				case "minimunAge" 	: o = e.getMinimunAge(); break;
				case "startDate" 	: o = e.getStartDate(); break;
				case "endDate" 		: o = e.getEndDate(); break;
				case "isopen" 		: Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
									o = (now.after(e.getStartDate()) && now.before(e.getEndDate()));
									break;
				case "rules" 		: o = e.getNormas(); break;
			}
			
			return Response.status(200).entity(o).build();
			
		} catch (ServiceException e) {
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
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	//ANONYMOUS
	
	
	//USER
	
	
	//ADMIN

}

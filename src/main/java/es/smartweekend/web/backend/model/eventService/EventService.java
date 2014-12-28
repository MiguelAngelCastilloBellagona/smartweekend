package es.smartweekend.web.backend.model.eventService;

import java.util.List;

import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;
import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public interface EventService {
	
	//ANONYMOUS
	
	public Event getEvent(int eventId) throws ServiceException;

	public boolean eventIsOpen(int eventId) throws ServiceException;
	
	public List<Event> getAllEvents() throws ServiceException;
	
	//USER
	
	
	//ADMIN
	
	public Event getEventADMIN(String sessionId, int eventId) throws ServiceException;
	
	public Event createEventADMIN(String sessionId, Event event) throws ServiceException;
	
	public void removeEventADMIN(String sessionId, int eventId) throws ServiceException;
	
	public Event changeEventDataADMIN(String sessionId, int eventId, Event eventData) throws ServiceException;
	
	public void SetSetPaidTemplateADMIN(String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void setOnQueueTemplateADMIN(String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void setOutstandingTemplateADMIN(String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void setOutOfDateTemplateADMIN(String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	public void setFromQueueToOutstandingADMIN(String sessionId, int eventId, int emailTemplateId) throws ServiceException;
	
	
	public EmailTemplate GetSetPaidTemplateADMIN(String sessionId, int eventId) throws ServiceException;
	
	public EmailTemplate GetOnQueueTemplateADMIN(String sessionId, int eventId) throws ServiceException;
	
	public EmailTemplate GetOutstandingTemplateADMIN(String sessionId, int eventId) throws ServiceException;
	
	public EmailTemplate GetOutOfDateTemplateADMIN(String sessionId, int eventId) throws ServiceException;
	
	public EmailTemplate GetFromQueueToOutstandingADMIN(String sessionId, int eventId) throws ServiceException;
	
}

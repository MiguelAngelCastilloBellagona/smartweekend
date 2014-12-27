package es.smartweekend.web.backend.model.eventService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;
import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("EventService")
@Transactional
public class EventServiceImpl implements EventService {

	//ANONYMOUS
	
	@Override
	public Event getEvent(int eventId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEventRules(int eventId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eventIsOpen(int eventId) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Event> getAllEvents() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//USER
	
	
	//ADMIN

	@Override
	public Event createEventADMIN(String sessionId, Event event)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEventADMIN(String sessionId, int eventId)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Event changeEventDataADMIN(String sessionId, int eventId,
			Event eventData) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SetSetPaidTemplateADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOnQueueTemplateADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOutstandingTemplateADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOutOfDateTemplateADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFromQueueToOutstandingADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public EmailTemplate GetSetPaidTemplateADMIN(String sessionId, int eventId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailTemplate GetOnQueueTemplateADMIN(String sessionId, int eventId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailTemplate GetOutstandingTemplateADMIN(String sessionId,
			int eventId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailTemplate GetOutOfDateTemplateADMIN(String sessionId, int eventId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailTemplate GetFromQueueToOutstandingADMIN(String sessionId,
			int eventId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}

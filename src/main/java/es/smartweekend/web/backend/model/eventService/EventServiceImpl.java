package es.smartweekend.web.backend.model.eventService;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;
import es.smartweekend.web.backend.model.emailTemplate.EmailTemplateDao;
import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.event.EventDao;
import es.smartweekend.web.backend.model.user.User;
import es.smartweekend.web.backend.model.user.UserDao;
import es.smartweekend.web.backend.model.util.exceptions.InstanceException;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("EventService")
@Transactional
public class EventServiceImpl implements EventService {

	private static final String EVENTVICEPERMISIONLEVEL = "E";
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	EmailTemplateDao emailTemplateDao;
	
	@Transactional(readOnly=true)
	public boolean checkPermissions(User user, String permisionLevelRequired) {
		try {
			return userDao.find(user.getUserId()).getPremissions().contains(permisionLevelRequired);
		} catch (InstanceException e) {
			return false;
		}
	}
	
	//ANONYMOUS
	
	@Transactional(readOnly = true)
	@Override
	public Event getEvent(int eventId) throws ServiceException {
		try {
			return eventDao.find(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean eventIsOpen(int eventId) throws ServiceException {
		Event event;
		try {
			event = eventDao.find(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		if(now.after(event.getStartDate()) && now.before(event.getEndDate())) return true;
		else return false;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Event> getAllEvents() throws ServiceException {
		return eventDao.getAllEvents();
	}
	
	//USER
	
	
	//ADMIN

	@Transactional
	@Override
	public Event createEventADMIN(String sessionId, Event event)
			throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		if(eventDao.findEventByName(event.getName())!=null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"name");
		if(event.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
		if(event.getDescription()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"description");
		if(event.getNormas()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"normas");
		if(event.getStartDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"startDate");
		if(event.getEndDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"endDate");
		if(event.getRegistrationOpenDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"registrationOpenDate");
		if(event.getRegistrationCloseDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"registrationCloseDate");
    	eventDao.save(event);
    	return event;
	}

	@Transactional
	@Override
	public void removeEventADMIN(String sessionId, int eventId)
			throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			eventDao.remove(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"event");
		}

	}

	@Transactional
	@Override
	public Event changeEventDataADMIN(String sessionId, int eventId,
			Event eventData) throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
		
			//int oldNumParticipants = event.getNumParticipants();
    		event.setEventId(eventId);
    		if(eventData.getName()!=null) event.setName(eventData.getName());
    		if(eventData.getDescription()!=null) event.setDescription(eventData.getDescription());
    	    if(eventData.getNumParticipants()>0) event.setNumParticipants(eventData.getNumParticipants());
    	    if(eventData.getMinimunAge()>=0) event.setMinimunAge(eventData.getMinimunAge());
    	    if(eventData.getPrice()>=0) event.setPrice(eventData.getPrice());
    		if(eventData.getStartDate()!=null) event.setStartDate(eventData.getStartDate());
    		if(eventData.getEndDate()!=null) event.setEndDate(eventData.getEndDate()); 
    		if(eventData.getRegistrationOpenDate()!=null) event.setRegistrationOpenDate(eventData.getRegistrationOpenDate());
    		if(eventData.getRegistrationCloseDate()!=null) event.setRegistrationCloseDate(eventData.getRegistrationCloseDate());
        	eventDao.save(event);
        	//if(eventData.getNumParticipants()>oldNumParticipants) eventNumParticipantsChanged(sessionId,eventId);
        	if(eventData.getNormas()!=null) event.setNormas(eventData.getNormas());
        	return event;
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"event");
		}
	}

	@Override
	public void SetSetPaidTemplateADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			event.setSetPaidTemplate(emailTemplateDao.find(emailTemplateId));
			eventDao.save(event);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public void setOnQueueTemplateADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			event.setOnQueueTemplate(emailTemplateDao.find(emailTemplateId));
			eventDao.save(event);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public void setOutstandingTemplateADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			event.setOutstandingTemplate(emailTemplateDao.find(emailTemplateId));
			eventDao.save(event);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public void setOutOfDateTemplateADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			event.setOutOfDateTemplate(emailTemplateDao.find(emailTemplateId));
			eventDao.save(event);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public void setFromQueueToOutstandingADMIN(String sessionId, int eventId,
			int emailTemplateId) throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			event.setFromQueueToOutstanding(emailTemplateDao.find(emailTemplateId));
			eventDao.save(event);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public EmailTemplate GetSetPaidTemplateADMIN(String sessionId, int eventId)
			throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			return event.getSetPaidTemplate();
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"event");
		}
	}

	@Override
	public EmailTemplate GetOnQueueTemplateADMIN(String sessionId, int eventId)
			throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			return event.getOnQueueTemplate();
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"event");
		}
	}

	@Override
	public EmailTemplate GetOutstandingTemplateADMIN(String sessionId,
			int eventId) throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			return event.getOutstandingTemplate();
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"event");
		}
	}

	@Override
	public EmailTemplate GetOutOfDateTemplateADMIN(String sessionId, int eventId)
			throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			return event.getOutOfDateTemplate();
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"event");
		}
	}

	@Override
	public EmailTemplate GetFromQueueToOutstandingADMIN(String sessionId,
			int eventId) throws ServiceException {
		try {
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), EVENTVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			Event event = eventDao.find(eventId);
			return event.getFromQueueToOutstanding();
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"event");
		}
	}

}

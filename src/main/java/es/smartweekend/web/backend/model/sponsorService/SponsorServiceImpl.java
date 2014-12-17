package es.smartweekend.web.backend.model.sponsorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.smartweekend.web.backend.model.event.EventDao;
import es.smartweekend.web.backend.model.sponsor.Sponsor;
import es.smartweekend.web.backend.model.sponsor.SponsorDao;
import es.smartweekend.web.backend.model.user.User;
import es.smartweekend.web.backend.model.user.UserDao;
import es.smartweekend.web.backend.model.util.exceptions.InstanceException;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("SponsorService")
@Transactional
public class SponsorServiceImpl implements SponsorService {

	private static final String SPONSORVICEPERMISIONLEVEL = "S";
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SponsorDao sponsorDao;
	
	@Autowired
	private EventDao eventDao;
	
	@Transactional(readOnly=true)
	public boolean checkPermissions(User user, String permisionLevelRequired) {
		try {
			return userDao.find(user.getUserId()).getPremissions().contains(permisionLevelRequired);
		} catch (InstanceException e) {
			return false;
		}
	}
	
	// ANONYMOUS

	@Override
	@Transactional(readOnly=true)
	public List<Sponsor> getAllEventSponsor(int eventId) throws ServiceException {
		return sponsorDao.getAllByEvent(eventId, 0, 0, "sponsorId", false);
	}

	// USER

	// ADMIN
	
	@Override
	public List<Sponsor> getAllSponsorADMIN(String sessionId, int startIndex, int cont, String orderBy, boolean desc)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		return sponsorDao.getAll(startIndex, cont, orderBy, desc);
	}

	public long getAllSponsorTAMADMIN(String sessionId) throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		return sponsorDao.getAllTAM();
	}
	
	@Override
	public Sponsor getSponsorADMIN(String sessionId, int sponsorId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			return sponsorDao.find(sponsorId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Sponsor");
		}
	}

	@Override
	public Sponsor addSponsorToEventADMIN(String sessionId, int eventId, Sponsor sponsor)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			sponsor.setEvent(eventDao.find(eventId));
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
		if(sponsor.getImageurl()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"ImageUrl");
		if(sponsor.getUrl()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"Url");
		if(sponsor.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"Name");
		sponsorDao.save(sponsor);
		return sponsor;
	}

	@Override
	public Sponsor changeSponsorADMIN(String sessionId, int sponsorId,
			Sponsor sponsorData) throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		Sponsor sponsor;
		try {
			sponsor = sponsorDao.find(sponsorId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Sposnor");
		}
		if(sponsorData.getImageurl()!=null) sponsor.setImageurl(sponsorData.getImageurl());
		if(sponsorData.getUrl()!=null) sponsor.setUrl(sponsorData.getUrl());
		if(sponsorData.getName()!=null) sponsor.setName(sponsorData.getName());
		return sponsor;
	}

	@Override
	public void removeSponsorADMIN(String sessionId, int sponsorId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), SPONSORVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.PERMISSION_DENIED);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			sponsorDao.remove(sponsorId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Sposnor");
		}
	}

}

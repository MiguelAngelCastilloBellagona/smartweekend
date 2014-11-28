package es.smartweekend.web.backend.model.sponsorService;

import java.util.List;

import es.smartweekend.web.backend.model.sponsor.Sponsor;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public interface SponsorService {

	//ANONYMOUS
	
	public List<Sponsor> getAllSponsor() throws ServiceException;
	
	//USER
	
	//ADMIN
	
	public Sponsor getSponsorADMIN(String sessionId, int sponsorId) throws ServiceException;
	
	public Sponsor addSponsorADMIN(String sessionId, Sponsor sponsor) throws ServiceException;
	
	public Sponsor changeSponsorADMIN(String sessionId, int sponsorId, Sponsor sponsorData) throws ServiceException;
	
	public void removeSponsorADMIN(String sessionId, int sponsorId) throws ServiceException;
	
}

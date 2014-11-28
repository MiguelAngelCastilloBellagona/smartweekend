package es.smartweekend.web.backend.model.sponsorService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.smartweekend.web.backend.model.sponsor.Sponsor;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("SponsorService")
@Transactional
public class SponsorServiceImpl implements SponsorService {

	// ANONYMOUS

	@Override
	public List<Sponsor> getAllSponsor() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	// USER

	// ADMIN

	@Override
	public Sponsor getSponsorADMIN(String sessionId, int sponsorId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sponsor addSponsorADMIN(String sessionId, Sponsor sponsor)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sponsor changeSponsorADMIN(String sessionId, int sponsorId,
			Sponsor sponsorData) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSponsorADMIN(String sessionId, int sponsorId)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

}

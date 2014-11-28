package es.smartweekend.web.backend.model.mailservice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.smartweekend.web.backend.model.adress.Adress;
import es.smartweekend.web.backend.model.email.Email;
import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("MailService")
@Transactional
public class MailServiceImpl implements MailService {

	// ANONYMOUS

	// USER

	@Override
	public boolean sendEmailUSER(String sessionId, int emailId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	// ADMIN

	@Override
	public Email addEmailADMIN(String sessionId, Email email)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Email modifyEmailAdressADMIN(String sessionId, int emailId,
			int adressId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Email modifyEmailADMIN(String sessionId, int emailID, Email email)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEmailADMIN(String sessionId, int emailId)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Email getEmailADMIN(String sessionId, int emailId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Email> getAllMailsADMIN(String sessionId, int startIndex,
			int cont, String orderBy, boolean desc) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Email> getConfirmedMailsADMIN(String sessionId, int startIndex,
			int cont, String orderBy, boolean desc) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Email> getNoConfirmedMailsADMIN(String sessionId,
			int startIndex, int cont, String orderBy, boolean desc)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendEmailADMIN(String sessionId, int emailId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Adress addAdressADMIN(String sessionId, Adress adress)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Adress modifyAdressADMIN(String sessionId, int adressId,
			Adress adressData) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAdressADMIN(String sessionId, int adressId)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public EmailTemplate addEmailtemplateADMIN(String sessionId,
			EmailTemplate emailTemplate) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailTemplate modifyEmailTemplateADMIN(String sessionId,
			int emailTemplateId, EmailTemplate emailTemplate)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEmailTemplateADMIN(String sessionId, int emailTemplateId)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public EmailTemplate getEmailTemplateADMIN(String sessionId,
			int emailTemplateId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailTemplate> getAllEmailTemplateADMIN(String sessionId,
			int startIndex, int cont, String orderBy, boolean desc)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailTemplate getEmailTemplateByNameADMIN(String sessionId,
			String name) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}

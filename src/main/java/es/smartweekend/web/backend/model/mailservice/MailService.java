package es.smartweekend.web.backend.model.mailservice;

import java.util.List;

import es.smartweekend.web.backend.model.adress.Adress;
import es.smartweekend.web.backend.model.email.Email;
import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public interface MailService {
	
	//ANONYMOUS
	
	
	//USER
	
	public boolean sendEmailUSER(String sessionId, int emailId) throws ServiceException;
	
	
	//ADMIN
	
	public Email addEmailADMIN(String sessionId, Email email) throws ServiceException;
	
	public Email modifyEmailAdressADMIN(String sessionId, int emailId, int adressId) throws ServiceException;
	
	public Email modifyEmailADMIN(String sessionId, int emailId, Email email) throws ServiceException;
	
	public void removeEmailADMIN(String sessionId, int emailId) throws ServiceException;
	
	public Email getEmailADMIN(String sessionId , int emailId) throws ServiceException;
	
	public List<Email> getAllMailsADMIN(String sessionId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
	
	public List<Email> getConfirmedMailsADMIN(String sessionId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
	
	public List<Email> getNoConfirmedMailsADMIN(String sessionId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
	
	public boolean sendEmailADMIN(String sessionId, int emailId) throws ServiceException;
	
	
	
	public Adress addAdressADMIN(String sessionId, Adress adress) throws ServiceException;
	
	public Adress modifyAdressADMIN(String sessionId, int adressId, Adress adressData) throws ServiceException;
	
	public void removeAdressADMIN(String sessionId, int adressId) throws ServiceException;
	
	
	
	public EmailTemplate addEmailtemplateADMIN(String sessionId, EmailTemplate emailTemplate) throws ServiceException;
	
	public EmailTemplate modifyEmailTemplateADMIN(String sessionId, int emailTemplateId, EmailTemplate emailTemplate) throws ServiceException;
	
	public void removeEmailTemplateADMIN(String sessionId, int emailTemplateId) throws ServiceException;
	
	public EmailTemplate getEmailTemplateADMIN(String sessionId, int emailTemplateId) throws ServiceException;
	
	public List<EmailTemplate> getAllEmailTemplateADMIN(String sessionId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException;
	
	public EmailTemplate getEmailTemplateByNameADMIN(String sessionId, String name) throws ServiceException;

}

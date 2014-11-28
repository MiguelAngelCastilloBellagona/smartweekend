package es.smartweekend.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.model.adress.Adress;
import es.smartweekend.web.backend.model.email.Email;
import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;
import es.smartweekend.web.backend.model.mailservice.MailService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class MailResource {
	
	@Autowired
	private MailService mailService;
	
	//ANONYMOUS
	
	
	//USER
	
	@Path("/sendMailUser/{emailId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public boolean sendEmailUSER(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId) throws ServiceException {
		return mailService.sendEmailUSER(sessionId, emailId);
	}
		
	//ADMIN
	
	@Path("/admin/addEmail")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Email addEmailADMIN(@HeaderParam("sessionId") String sessionId, Email email) throws ServiceException {
		return mailService.addEmailADMIN(sessionId, email);
	}
	
	@Path("/admin/modifyEmailAdress/{emailId}/{adressId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Email modifyEmailAdressADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId, @PathParam("adressId") int adressId) throws ServiceException {
		return mailService.modifyEmailAdressADMIN(sessionId, emailId, adressId);
	}
	
	@Path("/admin/modifyEmail/{emailId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Email modifyEmailADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId, Email email) throws ServiceException {
		return mailService.modifyEmailADMIN(sessionId, emailId, email);
	}

	@Path("/admin/removeEmail/{emailId}")
	@DELETE
	public void removeEmailADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId) throws ServiceException {
		mailService.removeEmailADMIN(sessionId, emailId);
	}
	
	@Path("/admin/getEmail/query")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Email getEmailADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId) throws ServiceException {
		return mailService.getEmailADMIN(sessionId, emailId);
	}
	
	@Path("/admin/getAllEmail")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Email> getAllMailsADMIN(@HeaderParam("sessionId") String sessionId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("date") @QueryParam("orderBy")         String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc
			) throws ServiceException {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return mailService.getAllMailsADMIN(sessionId,startIndex,cont,orderBy,b);
	}
	
	@Path("/admin/getConfirmedMails")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Email> getConfirmedMailsADMIN(@HeaderParam("sessionId") String sessionId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("date") @QueryParam("orderBy")         String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc
			) throws ServiceException {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return mailService.getConfirmedMailsADMIN(sessionId,startIndex,cont,orderBy,b);
	}
	
	@Path("/admin/getNoConfirmedMails")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Email> getNoConfirmedMailsADMIN(@HeaderParam("sessionId") String sessionId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("date") @QueryParam("orderBy")         String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc
			) throws ServiceException {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return mailService.getNoConfirmedMailsADMIN(sessionId,startIndex,cont,orderBy,b);
	}
	
	@Path("/admin/sendMail/{emailId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public boolean sendEmailADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emailId") int emailId) throws ServiceException {
		return mailService.sendEmailADMIN(sessionId, emailId);
	}
	
	@Path("/admin/addAdress")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Adress addAdressADMIN(@HeaderParam("sessionId") String sessionId, Adress adress) throws ServiceException {
		return mailService.addAdressADMIN(sessionId, adress);
	}
	
	@Path("/admin/modifyAdress/{adressId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Adress modifyEmailADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId, Adress adress) throws ServiceException {
		return mailService.modifyAdressADMIN(sessionId, adressId, adress);
	}
	
	@Path("/admin/removeAdress/{adressId}")
	@DELETE
	public void removeAdressADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("adressId") int adressId) throws ServiceException {
		mailService.removeAdressADMIN(sessionId, adressId);
	}
	
	@Path("/admin/addEmailTemplate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EmailTemplate addEmailtemplateADMIN(@HeaderParam("sessionId") String sessionId, EmailTemplate emailTemplate) throws ServiceException {
		return mailService.addEmailtemplateADMIN(sessionId, emailTemplate);
	}
	
	@Path("/admin/modifyEmailTemplate/{emaiTemplatelId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EmailTemplate modifyEmailTemplateADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emaiTemplatelId") int emaiTemplatelId, EmailTemplate emailTemplate) throws ServiceException {
		return mailService.modifyEmailTemplateADMIN(sessionId, emaiTemplatelId, emailTemplate);
	}

	@Path("/admin/removeEmailTemplate/{emailTemplateId}")
	@DELETE
	public void removeEmailTemplateADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emaiTemplatelId") int emaiTemplatelId) throws ServiceException {
		mailService.removeEmailTemplateADMIN(sessionId, emaiTemplatelId);
	}
	
	@Path("/admin/getEmailTemplate/{emailTemplateId}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public EmailTemplate addEmailtemplateADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emailTemplateId") int emailTemplateId) throws ServiceException {
		return mailService.getEmailTemplateADMIN(sessionId, emailTemplateId);
	}
	
	@Path("/admin/getAllEmailTemplate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<EmailTemplate> addEmailtemplateADMIN(@HeaderParam("sessionId") String sessionId,
			@DefaultValue("1") @QueryParam("page")               int page, 
			@DefaultValue("0") @QueryParam("pageTam")            int pageTam,
			@DefaultValue("name") @QueryParam("orderBy")         String orderBy,
			@DefaultValue("1") @QueryParam("desc")               int desc
			) throws ServiceException {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		return mailService.getAllEmailTemplateADMIN(sessionId,startIndex,cont,orderBy,b);
	}
	
	@Path("/admin/getEmailTemplateByName/{emailTemplateName}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public EmailTemplate addEmailtemplateADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("emailTemplateName") String emailTemplateName) throws ServiceException {
		return mailService.getEmailTemplateByNameADMIN(sessionId, emailTemplateName);
	}

}

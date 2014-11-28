package es.smartweekend.web.backend.model.email;

import java.util.List;

import es.smartweekend.web.backend.model.util.dao.GenericDao;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface EmailDao extends GenericDao<Email, Integer>{
	
	public List<Email> getAllEmails(int startIndex, int cont, String orderBy, boolean desc);
	
	public List<Email> getConfirmedEmails(int startIndex, int cont, String orderBy, boolean desc);
	
	public List<Email> getNoConfirmedEmails(int startIndex, int cont, String orderBy, boolean desc);
	
	public List<Email> getEmailByDestination(int userId, int startIndex, int cont, String orderBy, boolean desc);
	
	public List<Email> getEmailByDireccionEnvio(int direccionId, int startIndex, int cont, String orderBy, boolean desc);

}

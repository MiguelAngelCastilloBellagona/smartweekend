package es.smartweekend.web.backend.model.emailTemplate;

import java.util.List;

import es.smartweekend.web.backend.model.util.dao.GenericDao;


/**
 * @author Miguel Ángel Castillo Bellagona

 */
public interface EmailTemplateDao extends GenericDao<EmailTemplate, Integer> {

	public List<EmailTemplate> getAllEmailTemplate();
    
    public EmailTemplate findByName(String name);
}

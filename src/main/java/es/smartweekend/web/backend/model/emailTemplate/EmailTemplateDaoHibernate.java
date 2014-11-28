package es.smartweekend.web.backend.model.emailTemplate;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.smartweekend.web.backend.model.util.dao.GenericDaoHibernate;


/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Repository("EmailTemplateDao")
public class EmailTemplateDaoHibernate extends GenericDaoHibernate<EmailTemplate, Integer> implements EmailTemplateDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailTemplate> getAllEmailTemplate() {
		return getSession().createQuery( "SELECT e " +
				 "FROM EmailTemplate e ").list();
	}
	
	@Override
	public EmailTemplate findByName(String name) {
		return (EmailTemplate) getSession()
				.createQuery("SELECT e FROM EmailTemplate e WHERE e.name = :name")
				.setParameter("name", name).uniqueResult();
	}

}

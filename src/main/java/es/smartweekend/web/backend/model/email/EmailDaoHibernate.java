package es.smartweekend.web.backend.model.email;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.smartweekend.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("EmailDao")
public class EmailDaoHibernate extends GenericDaoHibernate<Email,Integer> implements EmailDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getAllEmails(int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"ORDER BY e." + orderBy + aux);
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getConfirmedEmails(int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.confirmation = true" +
	        	"ORDER BY e." + orderBy + aux);
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getNoConfirmedEmails(int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.confirmation = false" +
	        	"ORDER BY e" + orderBy + aux);
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getEmailByDestination(int destinatario, int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.destinatario.userId = :destino" +
	        	"ORDER BY e." + orderBy + aux
	        	).setParameter("destino", destinatario);
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Email> getEmailByDireccionEnvio(int direccionId, int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT e " +
		        "FROM Email e " +
	        	"WHERE e.adresslId = :direccionId" +
	        	"ORDER BY e." + orderBy + aux
	        	).setParameter("direccionId", direccionId);
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}
}

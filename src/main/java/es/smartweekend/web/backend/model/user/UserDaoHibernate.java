package es.smartweekend.web.backend.model.user;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.smartweekend.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Repository("UserDao")
public class UserDaoHibernate extends GenericDaoHibernate<User,Integer> implements UserDao {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers(int startindex, int maxResults, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT u " +
		        "FROM User u " +
		        "WHERE u.login!='anonymous'" +
	        	"ORDER BY u." + orderBy + aux );
		if(maxResults<1) return query.list();
		else return query.setFirstResult(startindex).setMaxResults(maxResults).list();
	}
	
	@Override
	public long getAllUsersTAM() {
		return (long) getSession().createQuery(
	        	"SELECT count(u) " +
		        "FROM User u " +
		        "WHERE u.login!='anonymous'"
		        ).uniqueResult();
	}
	
	@Override
	public User findUserBylogin(String login) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u WHERE User_login = :login")
				.setParameter("login", login).uniqueResult();
	}

	@Override
	public User findUserByEmail(String email) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u Where User_email = :email")
				.setParameter("email", email).uniqueResult();
	}

	@Override
	public User findUserByDni(String dni) {
		return (User) getSession()
				.createQuery("SELECT u FROM User u Where User_dni = :dni")
				.setParameter("dni", dni).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findUsersByName(String name) {
		return getSession().createQuery(
	        	"SELECT u " +
		        "FROM User u " +
		        "WHERE u.login!='anonymous' AND " +
		        "AND ( LOWER(u.name) LIKE '%'||LOWER(:name)||'%' " +
		        "OR  LOWER(u.login) LIKE '%'||LOWER(:name)||'%' ) " +
	        	"ORDER BY u.login").setParameter("name",name).list();
	}

}

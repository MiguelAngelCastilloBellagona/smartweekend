package es.smartweekend.web.backend.model.user;

import java.util.List;

import es.smartweekend.web.backend.model.util.dao.GenericDao;


/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
public interface UserDao extends GenericDao<User,Integer> {
	
	public List<User> getAllUsers(int startindex, int maxResults, String orderBy, boolean desc);
	
	public long getAllUsersTAM();
	
	public User findUserBylogin(String login);
	
	public User findUserByDni(String dni);
	
	public User findUserByEmail(String email);

	public List<User> findUsersByName(String name);

}

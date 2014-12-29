package es.smartweekend.web.backend.model.userService;

import java.util.List;

import es.smartweekend.web.backend.model.user.User;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.Session;
import es.smartweekend.web.backend.model.util.session.SessionData;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public interface UserService {
	
	public void initialize();

	//ANONYMOUS
	
	public boolean passwordRecover(String email) throws ServiceException;
    
	public User addUser(User user) throws ServiceException;
	
	public SessionData login(String login, String password)  throws ServiceException;
	
	
	//USER
	
	public User getCurrenUserUSER(String sessionId) throws ServiceException;
	
	public void removeUserUSER(String sessionId) throws ServiceException;
	
    public void changeUserDataUSER(String sessionId, User userData)  throws ServiceException;
	
	public void changeUserPasswordUSER(String sessionId, String oldPassword, String newPassword)  throws ServiceException;
	
	public String getUserPermissionsUSER(String sessionId) throws ServiceException;
	
	
	//ADMIN
	
	public List<Session> getAllUserSessionsADMIN(String sessionId, int userId) throws ServiceException;
	
	public void closeAllUserSessionsADMIN(String sessionId, int userId) throws ServiceException;
	
	public User getUserADMIN(String sessionId, int userId) throws ServiceException;
	
	public List<User> getAllUsersADMIN(String sessionId, int startIndex, int maxResults, String orderBy, boolean desc) throws ServiceException;
	
	public long getAllUsersTAMADMIN(String sessionId) throws ServiceException;
	
	public List<User> findUsersByNameADMIN(String sessionId, String name) throws ServiceException;
	
	public void removeUserADMIN(String sessionId, int userId) throws ServiceException;
	
    public void changeUserDataADMIN(String sessionId, int userId, User userData)  throws ServiceException;
	
	public void changeUserPasswordADMIN(String sessionId, int userId, String oldPassword, String newPassword)  throws ServiceException;
	
	public String getUserPermissionsADMIN(String sessionId, int userId) throws ServiceException;
	
	public String addUserPermissionsADMIN(String sessionId, int userId, String permission) throws ServiceException;
	
	public String removeUserPermissionsADMIN(String sessionId, int userId, String permission) throws ServiceException;
}

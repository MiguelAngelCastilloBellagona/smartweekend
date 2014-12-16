package es.smartweekend.web.backend.model.userService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.smartweekend.web.backend.model.email.Email;
import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;
import es.smartweekend.web.backend.model.emailTemplate.EmailTemplateDao;
import es.smartweekend.web.backend.model.user.User;
import es.smartweekend.web.backend.model.user.UserDao;
import es.smartweekend.web.backend.model.util.exceptions.InstanceException;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.Session;
import es.smartweekend.web.backend.model.util.session.SessionData;
import es.smartweekend.web.backend.model.util.session.SessionManager;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final String USERSERVICEPERMISIONLEVEL = "U";
	
	private static final String ADMIN_LOGIN = "Admin";
	private static final String INITIAL_ADMIN_PASS = "initialAdminPass";

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;

	@Override
	public void initialize() {

		User admin = userDao.findUserBylogin(ADMIN_LOGIN);
		if (admin == null)
			admin = new User("EMNRSU", "Administrador", ADMIN_LOGIN,
					hashPassword(INITIAL_ADMIN_PASS), "0", "adminMail", "-",
					"-", Calendar.getInstance(TimeZone.getTimeZone("UTC")),
					"ES");
		userDao.save(admin);

	}

	@Transactional(readOnly=true)
	public boolean checkPermissions(User user, String permisionLevelRequired) {
		try {
			return userDao.find(user.getUserId()).getPremissions().contains(permisionLevelRequired);
		} catch (InstanceException e) {
			return false;
		}
	}
	
	private String hashPassword(String password) {
		try {
			MessageDigest m = MessageDigest.getInstance("SHA-512");

			m.reset();
			m.update(password.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private String generatePassword() {
		int passtam = 12;
		char[] elementos={'0','1','2','3','4','5','6','7','8','9' ,'a',
				'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
				'u','v','w','x','y','z'};
		
		char[] conjunto = new char[passtam];
		
		for(int i=0;i<passtam;i++){
			int el = (int)(Math.random()*35);
			conjunto[i] = (char)elementos[el];
		}
		return new String(conjunto);
	}

	// ANONYMOUS

	@Override
	@Transactional
	public boolean passwordRecover(String email) throws ServiceException {
		int minutos = 30;
		
		User user = userDao.findUserByEmail(email);
		EmailTemplate template = emailTemplateDao.findByName("passwordRecover");
		
		if((user!=null) && (template!=null)) { 
			
			String pass = generatePassword();
			
			user.setSecondPassword(hashPassword(pass));
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			now.add(Calendar.MINUTE,minutos); 
			user.setSecondPasswordExpDate(now);
			
			Hashtable<String,String> tabla = new Hashtable<String,String>();
    		tabla.put("#loginusuario", user.getLogin());
    		tabla.put("#nuevapas", pass);
    		tabla.put("#tiemporestante",Integer.toString(minutos));
			 		
    		Email e = emailTemplateDao.findByName("passwordRecover").generateEmail(user, tabla);

    		if(e.sendMail()) userDao.save(user);
    		
    		return true;
		}
		return false;
	}

	@Override
	@Transactional
	public User addUser(User user) throws ServiceException {
		User u = userDao.findUserBylogin(user.getLogin());
		if (u != null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"login");
		u = userDao.findUserByDni(user.getDni());
		if (u != null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"dni");
		if(user.getLogin()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"login");
		if(user.getPassword()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"password");
		if(user.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
		if(user.getDni()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"dni");
		u = userDao.findUserByEmail(user.getEmail());
		if (u != null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"email");
		if(user.getEmail()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"email");
		if(user.getDob()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"dob");
		if(user.getLanguage()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"Language");
		user.setPremissions("");
		String pass = hashPassword(user.getPassword());
		user.setPassword(pass);
		user.setSecondPassword(pass);
		userDao.save(user);
		return user;	
	}

	@Override
	@Transactional(readOnly=true)
	public SessionData login(String login, String password) throws ServiceException {	
		User user = userDao.findUserBylogin(login);
		boolean secondPass = false;
		if (user == null) throw new ServiceException(ServiceException.INCORRECT_FIELD,"User/Password");
		if (!user.getPassword().contentEquals(hashPassword(password))) {
			if ((user.getSecondPasswordExpDate()==null) || (user.getSecondPasswordExpDate().before(Calendar.getInstance(TimeZone.getTimeZone("UTC")))) || (!user.getSecondPassword().contentEquals(hashPassword(password)))) {
				throw new ServiceException(ServiceException.INCORRECT_FIELD,"User/Password"); 
			}
			else secondPass = true;
		}
		Session session = new Session(user);
		SessionManager.addSession(session);
		return new SessionData(session.getSessionId(),user.getUserId(),secondPass,user.getLogin(), user.getPremissions(), user.getLanguage());
	}

	// USER

	@Override
	@Transactional(readOnly=true)
	public User getCurrenUserUSER(String sessionId) throws ServiceException {
		try {
			return userDao.find(SessionManager.getSession(sessionId).getUserId());
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	@Override
	@Transactional
	public void removeUserUSER(String sessionId) throws ServiceException {
		try {
			userDao.remove(SessionManager.getSession(sessionId).getUserId());
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	@Override
	@Transactional
	public void changeUserDataUSER(String sessionId, User userData) throws ServiceException {
		Session session = SessionManager.getSession(sessionId);
		try {
			User user = userDao.find(session.getUserId());
			if(userData.getName()!=null) user.setName(userData.getName());
			User u = userDao.findUserByEmail(userData.getEmail());
			if(u!=null) 
				if(!(u.getUserId()==user.getUserId())) 
					throw new ServiceException(ServiceException.DUPLICATED_FIELD,"email");
			if(userData.getEmail()!=null) user.setEmail(userData.getEmail());
			if(userData.getPhoneNumber()!=null )user.setPhoneNumber(userData.getPhoneNumber());
			if(userData.getShirtSize()!=null) user.setShirtSize(userData.getShirtSize());
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	@Override
	@Transactional
	public void changeUserPasswordUSER(String sessionId, String oldPassword, String newPassword) throws ServiceException {
		Session session = SessionManager.getSession(sessionId);
		try {
			User user = userDao.find(session.getUserId());
			if(!hashPassword(oldPassword).contentEquals(user.getPassword())) 
					if ((user.getSecondPasswordExpDate()==null) || (user.getSecondPasswordExpDate().before(Calendar.getInstance(TimeZone.getTimeZone("UTC")))) || (!user.getSecondPassword().contentEquals(hashPassword(oldPassword)))) 
						throw new ServiceException(ServiceException.INCORRECT_FIELD,"pass"); 
			user.setSecondPasswordExpDate(null);
			user.setPassword(hashPassword(newPassword));
			user.setSecondPassword(user.getSecondPassword());
			userDao.save(user);	
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}	
	}

	@Override
	@Transactional(readOnly = true)
	public String getUserPermissionsUSER(String sessionId)
			throws ServiceException {
		Session session = SessionManager.getSession(sessionId);
		try {
			return userDao.find(session.getUserId()).getPremissions();
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	// ADMIN

	@Override
	@Transactional(readOnly = true)
	public List<Session> getAllUserSessionsADMIN(String sessionId, int userId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		SessionManager.getAllUserSessions(userId);
		
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public void closeAllUserSessionsADMIN(String sessionId, int userId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		SessionManager.closeAllUserSessions(userId);

	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsersADMIN(String sessionId, int startIndex,
			int maxResults, String orderBy, boolean desc)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		return userDao.getAllUsers(startIndex, maxResults, orderBy, desc);
	}

	@Override
	@Transactional(readOnly = true)
	public long getAllUsersTAMADMIN(String sessionId) throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		return userDao.getAllUsersTAM();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findUsersByNameADMIN(String sessionId, String name) 
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		return userDao.findUsersByName(name);
	}

	@Override
	@Transactional
	public void removeUserADMIN(String sessionId, int userId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			userDao.remove(userId);
		} catch (InstanceException e) {}

	}

	@Override
	@Transactional
	public void changeUserDataADMIN(String sessionId, int userId, User userData)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			User user = userDao.find(userId);
			if(userData.getName()!=null) user.setName(userData.getName());
			User u = userDao.findUserByEmail(userData.getEmail());
			if(u!=null) 
				if(!(u.getUserId()==user.getUserId())) 
					throw new ServiceException(ServiceException.DUPLICATED_FIELD,"email");
			if(userData.getEmail()!=null) user.setEmail(userData.getEmail());
			if(userData.getPhoneNumber()!=null )user.setPhoneNumber(userData.getPhoneNumber());
			if(userData.getShirtSize()!=null) user.setShirtSize(userData.getShirtSize());
			if(userData.getDni()!=null )user.setDni(userData.getDni());
			if(userData.getDob()!=null )user.setDob(userData.getDob());
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	@Override
	@Transactional
	public void changeUserPasswordADMIN(String sessionId, int userId,
			String oldPassword, String newPassword) throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			User user = userDao.find(userId);
			user.setSecondPasswordExpDate(null);
			user.setPassword(hashPassword(newPassword));
			user.setSecondPassword(user.getSecondPassword());
			userDao.save(user);	
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}	
	}

	@Override
	@Transactional(readOnly = true)
	public String getUserPermissionsADMIN(String sessionId, int userId)
			throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			return userDao.find(userId).getPremissions();
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	@Override
	@Transactional
	public String addUserPermissionsADMIN(String sessionId, int userId,
			String permission) throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			User user = userDao.find(userId);
			if(!user.getPremissions().contains(permission)) 
			{
				user.setPremissions(user.getPremissions() + permission);
				userDao.save(user);
				return user.getPremissions();
			}
			return user.getPremissions();
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	@Override
	public String removeUserPermissionsADMIN(String sessionId, int userId,
			String permission) throws ServiceException {
		try { 
			if(!checkPermissions(userDao.find(SessionManager.getSession(sessionId).getUserId()), USERSERVICEPERMISIONLEVEL))
				throw new ServiceException(ServiceException.INVALID_SESSION);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"user");
		}
		try {
			User user = userDao.find(userId);
			if(!user.getPremissions().contains(permission)) 
			{
				user.setPremissions(user.getPremissions().replace(permission,""));
				userDao.save(user);
				return user.getPremissions();
			}
			return user.getPremissions();
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

}

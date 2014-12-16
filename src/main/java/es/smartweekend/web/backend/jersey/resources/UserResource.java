package es.smartweekend.web.backend.jersey.resources;

import java.util.ArrayList;
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
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.jersey.util.ApplicationContextProvider;
import es.smartweekend.web.backend.model.mailservice.MailService;
import es.smartweekend.web.backend.model.user.User;
import es.smartweekend.web.backend.model.userService.UserService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;
import es.smartweekend.web.backend.model.util.session.Session;
import es.smartweekend.web.backend.model.util.session.SessionData;
import es.smartweekend.web.backend.model.util.session.SessionManager;


/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Path("user")
public class UserResource {
	
	private String[] s = {"userId","name","login","dni","email","phoneNumber","shirtSize","dob"};
	private ArrayList<String> l;
	
	static class ChangePasswordData {
		private String oldPassword;
		private String newPassword;

		public ChangePasswordData(){}
		public ChangePasswordData(String oldPassword, String newPassword) {
			super();
			this.oldPassword = oldPassword;
			this.newPassword = newPassword;
		}
		public String getOldPassword() {
			return oldPassword;
		}
		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}
		public String getNewPassword() {
			return newPassword;
		}
		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}	
	}
	
	static class LoginData {

		private String login;
		private String password;

		public LoginData() {
		}

		public LoginData(String login, String password) {
			this.login = login;
			this.password = password;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private MailService mailService;
    
	public UserResource(){
		this.userService  = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
		this.mailService = ApplicationContextProvider.getApplicationContext().getBean(MailService.class);
		l = new ArrayList<String>();
		l.add(s[0]);l.add(s[1]);l.add(s[2]);l.add(s[3]);l.add(s[4]);l.add(s[5]);l.add(s[6]);l.add(s[7]);
	}
    
	//ANONYMOUS
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response adduser(User user) {
		try {
			User u = userService.addUser(user);
			return Response.status(201).entity(u).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/passwordrecover/")
	@POST
	@Consumes("text/plain")
	public Response passwordRecover(String email) {
		try {
			boolean b =  userService.passwordRecover(email);
			return Response.status(200).entity(b).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}	
	
	@Path("/login/")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@HeaderParam("sessionId") String sessionId, LoginData loginData) {
		try {
			SessionData u = userService.login(loginData.getLogin(), loginData.getPassword());
			return Response.status(200).entity(u).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	//USER
	
	@Path("/session/")
	@DELETE
	public Response closeSessionUSER(@HeaderParam("sessionId") String sessionId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			SessionManager.removeSession(sessionId);
			return Response.status(204).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@DELETE
	@Consumes({MediaType.APPLICATION_JSON})
	public Response removeUserUSER(@HeaderParam("sessionId") String sessionId)  {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			userService.removeUserUSER(sessionId);
			return Response.status(204).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/{userId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	public Response changeDataUSER(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, User user) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			userService.changeUserDataUSER(sessionId, user);
			return Response.status(204).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/changePassword/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response changePasswordUSER(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, ChangePasswordData data) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			userService.changeUserPasswordUSER(sessionId, data.getOldPassword(), data.getNewPassword());
			return Response.status(204).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

	@Path("/currentUser/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response currentUserUSER(@HeaderParam("sessionId") String sessionId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			User u = userService.getCurrenUserUSER(sessionId);
			return Response.status(200).entity(u).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/getPermissions/")
	@GET
	@Produces("text/plain")
	public Response getUserPermissionsUSER(@HeaderParam("sessionId") String sessionId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			String p = userService.getUserPermissionsUSER(sessionId);
			return Response.status(200).entity(p).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	//ADMIN
	
	@Path("/admin/allUserSession/{userId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getSllUserSessionsADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			List<Session> l = userService.getAllUserSessionsADMIN(sessionId,userId);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/closeAllUserSession/{userId}")
	@DELETE
	public Response closeAllUserSessionsADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			userService.closeAllUserSessionsADMIN(sessionId,userId);
			return Response.status(203).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}	
	}
	
	@Path("/admin/getAllUser/query")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllUsersADMIN(@HeaderParam("sessionId") String sessionId,
			@PathParam("eventId")                          int eventId,
			@DefaultValue("1") @QueryParam("page")         int page, 
			@DefaultValue("0") @QueryParam("pageTam")      int pageTam,
			@DefaultValue("login") @QueryParam("orderBy")  String orderBy,
			@DefaultValue("1") @QueryParam("desc")         int desc) {
		int startIndex = page*pageTam - pageTam;
		int cont = pageTam;
		boolean b = true;
		if(desc==0) b = false;
		try {
			if(l.indexOf(orderBy)<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"orderBy");
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			List<User> l = userService.getAllUsersADMIN(sessionId,startIndex,cont,orderBy,b);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/getAllUserTAM/")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllUsersTAMADMIN(@HeaderParam("sessionId") String sessionId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			long l = userService.getAllUsersTAMADMIN(sessionId);
			return Response.status(200).entity(l).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/delete/{userId}")
	@DELETE
	@Consumes({MediaType.APPLICATION_JSON})
	public Response removeUserADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			userService.removeUserADMIN(sessionId, userId);
			return Response.status(203).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/changeData/{userId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	public Response changeDataADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, User user) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			userService.changeUserDataADMIN(sessionId, userId, user);
			return Response.status(203).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/changePassword/{userId}")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response changePasswordADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, ChangePasswordData data) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			userService.changeUserPasswordADMIN(sessionId, userId, data.getOldPassword(), data.getNewPassword());
			return Response.status(203).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/getPermissions/{userId}")
	@GET
	@Produces("text/plain")
	public Response getUserPermissionsADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			String s = userService.getUserPermissionsADMIN(sessionId, userId);
			return Response.status(200).entity(s).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/addPermissions/{userId}/{permission}")
	@POST
	@Produces("text/plain")
	public Response addUserPermissionsADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, @PathParam("permission") String permission) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			String s = userService.addUserPermissionsADMIN(sessionId, userId, permission);
			return Response.status(200).entity(s).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}
	
	@Path("/admin/removePermissions/{userId}/{permission}")
	@DELETE
	@Produces("text/plain")
	public Response removeUserPermissionsADMIN(@HeaderParam("sessionId") String sessionId, @PathParam("userId") int userId, @PathParam("permission") String permission) {
		try {
			if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
			String s = userService.removeUserPermissionsADMIN(sessionId, userId, permission);
			return Response.status(200).entity(s).build();
		} catch (ServiceException e) {
			return Response.status(e.getHttpErrorCode()).entity(e.toString()).build();
		}
	}

}

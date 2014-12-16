package es.smartweekend.web.backend.model.util.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;


public class SessionManager {
	
	private static ConcurrentHashMap<String, Session> openSessions = new ConcurrentHashMap<String, Session>();
	
	public static boolean exists(String sessionId){
		return openSessions.containsKey(sessionId);
	}
	
	public static void addSession(Session s){
		openSessions.put(s.getSessionId(), s);
	}
	
	public static Session getSession(String sessionId){
		Session s = openSessions.get(sessionId);
		s.setLastAccess(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		return s;
	}
	
	public static void removeSession(String sessionId){
		openSessions.remove(sessionId);
	}
	
	public static void cleanOldSessions(int timeout){
		Calendar limitTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		limitTime.add(Calendar.SECOND, -timeout);
		for(Map.Entry<String,Session> e:openSessions.entrySet()){
			if(e.getValue().getLastAccess().before(limitTime))
				openSessions.remove(e.getKey());
		}
	}
	
	public static List<Session> getAllUserSessions(int userId) {
		List<Session> keys = new ArrayList<Session>();
		for (Entry<String, Session> e : openSessions.entrySet()) {
            Session value = e.getValue();
            if(value.getUserId()==userId) keys.add(value);
        }
		return keys;
	}
	
	public static void closeAllUserSessions(int userId) {
		List<String> keys = new ArrayList<String>();
		for (Entry<String, Session> e : openSessions.entrySet()) {
            String key = e.getKey();
            Session value = e.getValue();
            if(value.getUserId()==userId) keys.add(key);
        }
		
		for(String s : keys) {
			removeSession(s);
		}
	}
}

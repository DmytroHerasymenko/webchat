package ua.training.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 09.03.17.
 */
@WebListener
public class CreateHttpSessionListener implements HttpSessionListener {
    private static Map<String, HttpSession> sessions = new HashMap<>();
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String sessionId = session.getId();
        sessions.put(sessionId, session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String sessionId = session.getId();
        sessions.remove(sessionId);
    }

    public static HttpSession getSessionById(String sessionId){
        return sessions.get(sessionId);
    }
}

package fhv.ws22.se.skyward.domain;

import java.math.BigInteger;
import java.util.HashMap;

public class SessionFactory {
    private static SessionFactory instance;
    private HashMap<BigInteger, Session> sessions;

    private SessionFactory() {
        sessions = new HashMap<BigInteger, Session>();
    }

    public static SessionFactory getInstance() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance;
    }

    public Session getSession(BigInteger id) {
        if (sessions.get(id) == null) {
            Session session = new Session();
            sessions.put(id, session);
        }
        return sessions.get(id);
    }
}

package fhv.ws22.se.skyward.domain;

public class SessionFactory {
    private static SessionFactory instance;

    private SessionFactory() {
    }

    public static SessionFactory getInstance() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance;
    }

    public Session getSession() {
        return Session.getInstance();
    }
}

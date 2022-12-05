package fhv.ws22.se.skyward.domain;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fhv.ws22.se.skyward.AppConfig;
import fhv.ws22.se.skyward.persistence.DataGenerator;

import java.math.BigInteger;
import java.util.HashMap;

public class SessionFactory {
    private static SessionFactory instance;
    private HashMap<BigInteger, Session> sessions;
    private Injector injector;

    private SessionFactory() {
        sessions = new HashMap<>();

        DataGenerator dataGenerator = new DataGenerator();
        injector = Guice.createInjector(new AppConfig());
        injector.injectMembers(dataGenerator);
        dataGenerator.generateData();
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
            injector.injectMembers(session);
            sessions.put(id, session);
        }
        return sessions.get(id);
    }
}

package com.aip.util;

import com.aip.model.Candidate;
import com.aip.model.Vote;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Builds one Hibernate SessionFactory for the whole application (configured in code, no XML needed). */
public class HibernateUtil {
    private static SessionFactory factory;

    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null || factory.isClosed()) {
            factory = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                    .setProperty("hibernate.connection.url", DBUtil.URL)
                    .setProperty("hibernate.connection.username", DBUtil.USER)
                    .setProperty("hibernate.connection.password", DBUtil.PASS)
                    .setProperty("hibernate.hbm2ddl.auto", "update")
                    .setProperty("hibernate.show_sql", "false")
                    .addAnnotatedClass(Candidate.class)
                    .addAnnotatedClass(Vote.class)
                    .buildSessionFactory();
        }
        return factory;
    }

    public static synchronized void shutdown() {
        if (factory != null && !factory.isClosed()) factory.close();
    }
}

package com.gloresoft.weatherapp.backend.util;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
public class HibernateUtil {
    private static SessionFactory sessionFactory = getSessionFactory();

    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null)
                sessionFactory = new Configuration().configure().buildSessionFactory();

            return sessionFactory;
        } catch (Exception e) {
            log.error("Exception while creating session factory" + e);
            throw new RuntimeException(e);
        }
    }

}

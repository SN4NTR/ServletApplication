package com.leverx.servletapp.db;

import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static javax.persistence.Persistence.createEntityManagerFactory;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class HibernateConfig {

    private static final String persistenceUnitName = "JpaPersistence";
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = createEntityManagerFactory(persistenceUnitName);
    }

    public static synchronized EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}

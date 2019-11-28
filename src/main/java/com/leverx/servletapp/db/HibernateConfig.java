package com.leverx.servletapp.db;

import lombok.NoArgsConstructor;

import javax.persistence.EntityManagerFactory;

import static java.util.Objects.isNull;
import static javax.persistence.Persistence.createEntityManagerFactory;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class HibernateConfig {

    private static EntityManagerFactory entityManagerFactory;

    private static final String persistenceUnitName = "JpaPersistence";

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (isNull(entityManagerFactory)) {
            entityManagerFactory = createEntityManagerFactory(persistenceUnitName);
        }
        return entityManagerFactory;
    }
}

package com.leverx.servletapp.db;

import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EntityManagerConfig {

    private static final String PERSISTENCE_UNIT_NAME = "JpaPersistence";
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public static void createEntityManagerFactory() {
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        ENTITY_MANAGER_FACTORY.close();
    }
}

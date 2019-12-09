package com.leverx.servletapp.db;

import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class EntityManagerConfig {

    private static final String PERSISTENCE_UNIT_NAME = "JpaPersistence";
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;

    public static void createEntityManagerFactory() {
        var properties = new HashMap<String, Object>();
        properties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://database:3306/db");
        properties.put("javax.persistence.jdbc.user", "user");
        properties.put("javax.persistence.jdbc.password", "password");
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        ENTITY_MANAGER_FACTORY.close();
    }
}

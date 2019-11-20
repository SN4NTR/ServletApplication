package com.leverx.servletapp.db;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.user.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateConfig {

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Cat.class);

        Properties configProperties = configuration.getProperties();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configProperties)
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}

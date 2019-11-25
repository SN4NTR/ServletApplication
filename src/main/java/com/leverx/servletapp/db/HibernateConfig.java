package com.leverx.servletapp.db;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.user.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    public static SessionFactory getSessionFactory() {
        var configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Cat.class);

        var configProperties = configuration.getProperties();
        var serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configProperties)
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}

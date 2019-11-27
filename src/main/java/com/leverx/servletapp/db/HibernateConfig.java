package com.leverx.servletapp.db;

import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.user.entity.User;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getInstance() {
        if (sessionFactory == null) {
            var configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Cat.class);

            var configProperties = configuration.getProperties();
            var serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configProperties)
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}

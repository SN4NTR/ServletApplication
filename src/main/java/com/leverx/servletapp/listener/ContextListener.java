package com.leverx.servletapp.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.leverx.servletapp.context.ApplicationContext.loadApplicationContext;
import static com.leverx.servletapp.db.EntityManagerConfig.closeEntityManagerFactory;
import static com.leverx.servletapp.db.EntityManagerConfig.createEntityManagerFactory;

@Slf4j
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context initialized.");
        loadApplicationContext();
        createEntityManagerFactory();
        log.info("EntityManagerFactory is created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Context destroyed.");
        closeEntityManagerFactory();
        log.info("EntityManagerFactory is closed");
    }
}

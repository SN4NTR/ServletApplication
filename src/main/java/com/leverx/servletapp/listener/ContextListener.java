package com.leverx.servletapp.listener;

import org.slf4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static org.slf4j.LoggerFactory.getLogger;

public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = getLogger(ContextListener.class.getSimpleName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Context initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Context destroyed.");
    }
}

package com.leverx.servletapp.user.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class.getSimpleName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Context initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Context destroyed.");
    }
}

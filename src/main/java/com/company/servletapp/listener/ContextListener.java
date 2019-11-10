package com.company.servletapp.listener;

import com.company.servletapp.logger.ServletLogger;
import org.slf4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private final Logger logger;

    public ContextListener() {
        this.logger = ServletLogger.getLogger();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Context initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Context destroyed.");
    }
}

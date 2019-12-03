package com.leverx.servletapp.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Context destroyed.");
    }
}

package com.company.servletapp.listener;

import com.company.servletapp.logger.ServletLogger;
import org.slf4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestListener implements ServletRequestListener {

    private final Logger logger;

    public RequestListener() {
        this.logger = ServletLogger.getLogger();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();

        logger.info("Request initialized.");
        logger.info("Remote IP: {}", servletRequest.getRemoteAddr());
        logger.info("Protocol: {}", servletRequest.getProtocol());
        logger.info("Content type: {}", servletRequest.getContentType());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        logger.info("Request destroyed.");
    }
}

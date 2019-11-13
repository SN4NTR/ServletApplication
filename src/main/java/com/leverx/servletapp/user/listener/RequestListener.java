package com.leverx.servletapp.user.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestListener implements ServletRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(RequestListener.class.getSimpleName());

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

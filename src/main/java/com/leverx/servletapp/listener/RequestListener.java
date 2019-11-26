package com.leverx.servletapp.listener;

import org.slf4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import static org.slf4j.LoggerFactory.getLogger;

public class RequestListener implements ServletRequestListener {

    private static final Logger LOGGER = getLogger(RequestListener.class.getSimpleName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        var servletRequest = sre.getServletRequest();

        LOGGER.info("Request initialized.");
        LOGGER.info("Remote IP: {}", servletRequest.getRemoteAddr());
        LOGGER.info("Protocol: {}", servletRequest.getProtocol());
        LOGGER.info("Content type: {}", servletRequest.getContentType());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        LOGGER.info("Request destroyed.");
    }
}

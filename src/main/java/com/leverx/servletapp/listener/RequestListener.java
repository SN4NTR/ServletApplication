package com.leverx.servletapp.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

@Slf4j
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        var servletRequest = sre.getServletRequest();

        log.info("Request initialized.");
        log.info("Remote IP: {}", servletRequest.getRemoteAddr());
        log.info("Protocol: {}", servletRequest.getProtocol());
        log.info("Content type: {}", servletRequest.getContentType());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("Request destroyed.");
    }
}

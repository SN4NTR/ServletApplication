package com.leverx.servletapp.web.servlet.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import static java.util.Locale.setDefault;

@Slf4j
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        var servletRequest = sre.getServletRequest();
        var locale = servletRequest.getLocale();
        setDefault(locale);

        log.info("Request initialized.");
        log.info("Remote IP: {}", servletRequest.getRemoteAddr());
        log.info("Protocol: {}", servletRequest.getProtocol());
        log.info("Locale: {}", locale);
        log.info("Content type: {}", servletRequest.getContentType());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("Request destroyed.");
    }
}

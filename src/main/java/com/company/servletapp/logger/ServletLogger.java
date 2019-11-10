package com.company.servletapp.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletLogger {

    private ServletLogger() {
    }

    public static Logger getLogger() {
        final String LOGGER_NAME = "ServletLogger";

        return LoggerFactory.getLogger(LOGGER_NAME);
    }
}

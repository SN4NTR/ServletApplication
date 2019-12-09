package com.leverx.servletapp.db;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.getenv;

public class PropertyLoader {

    private static final String DB_URL_ENV_NAME = "DB_URL";
    private static final String USER_ENV_NAME = "DB_LOGIN";
    private static final String PASSWORD_ENV_NAME = "DB_PASSWORD";

    private static final String DRIVER_PROPERTY_NAME = "javax.persistence.jdbc.driver";
    private static final String DRIVER_PROPERTY_VALUE = "com.mysql.cj.jdbc.Driver";

    private static final String URL_PROPERTY_NAME = "javax.persistence.jdbc.url";
    private static final String USER_PROPERTY_NAME = "javax.persistence.jdbc.user";
    private static final String PASSWORD_PROPERTY_NAME = "javax.persistence.jdbc.password";

    public static Map<String, Object> getEnvVariable() {
        var dbUrlValue = getenv(DB_URL_ENV_NAME);
        var userValue = getenv(USER_ENV_NAME);
        var passwordValue = getenv(PASSWORD_ENV_NAME);

        var properties = new HashMap<String, Object>();
        properties.put(DRIVER_PROPERTY_NAME, DRIVER_PROPERTY_VALUE);
        properties.put(URL_PROPERTY_NAME, dbUrlValue);
        properties.put(USER_PROPERTY_NAME, userValue);
        properties.put(PASSWORD_PROPERTY_NAME, passwordValue);
        return properties;
    }
}

package com.leverx.servletapp.database;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class DataBaseProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseProperties.class.getSimpleName());

    private static final String FILE_NAME = "database.properties";

    static final String driverClassName;
    static final String username;
    static final String password;
    static final String dataBaseUrl;

    static {
        try (InputStream inputStream = DataBaseProperties.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            driverClassName = properties.getProperty("db.datasource.driver-class-name");
            username = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            dataBaseUrl = properties.getProperty("db.url");
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());

            throw new InternalServerErrorException();
        }
    }
}

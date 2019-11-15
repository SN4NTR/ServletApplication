package com.leverx.servletapp.db;

import com.leverx.servletapp.db.constant.PropertyName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.leverx.servletapp.db.constant.PropertyName.DRIVER;
import static com.leverx.servletapp.db.constant.PropertyName.FILE_NAME;
import static com.leverx.servletapp.db.constant.PropertyName.PASSWORD;
import static com.leverx.servletapp.db.constant.PropertyName.URL;
import static com.leverx.servletapp.db.constant.PropertyName.USERNAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class DBPropertyLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBPropertyLoader.class.getSimpleName());

    private static final String PROPERTIES_FILE_NAME = FILE_NAME.getValue();

    static final String DB_DRIVER;
    static final String DB_USERNAME;
    static final String DB_PASSWORD;
    static final String DB_URL;

    static {
        try (InputStream inputStream = DBPropertyLoader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            var properties = new Properties();
            properties.load(inputStream);

            DB_DRIVER = properties.getProperty(DRIVER.getValue());
            DB_USERNAME = properties.getProperty(USERNAME.getValue());
            DB_PASSWORD = properties.getProperty(PASSWORD.getValue());
            DB_URL = properties.getProperty(URL.getValue());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException();
        }
    }
}

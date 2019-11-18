package com.leverx.servletapp.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.leverx.servletapp.db.constant.PropertyName.FILE_NAME;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class PropertyHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyHolder.class.getSimpleName());

    private static final String PROPERTIES_FILE_NAME = FILE_NAME.getValue();

    static final Map<String, String> dbProperties = new HashMap<>();

    static {
        try (InputStream inputStream = PropertyHolder.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            var properties = new Properties();
            properties.load(inputStream);

            var propertiesNames = properties.stringPropertyNames();

            for (var key : propertiesNames) {
                var value = properties.getProperty(key);
                dbProperties.put(key, value);

                LOGGER.info("Key {} retrieved", key);
            }
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException();
        }
    }
}

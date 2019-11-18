package com.leverx.servletapp.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static com.leverx.servletapp.db.constant.PropertyName.FILE_NAME;
import static java.util.stream.Collectors.toMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class PropertyHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyHolder.class.getSimpleName());

    static Map<String, String> getDataBaseProperties() {
        final String PROPERTIES_FILE_NAME = FILE_NAME.getValue();

        try (InputStream inputStream = PropertyHolder.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            var properties = new Properties();
            properties.load(inputStream);

            return properties.entrySet()
                    .stream()
                    .collect(toMap(
                            entry -> (String) entry.getKey(),
                            entry -> (String) entry.getValue()));
        } catch (IOException ex) {
            LOGGER.error("Properties can't be loaded");
            throw new InternalServerErrorException(ex);
        }
    }
}

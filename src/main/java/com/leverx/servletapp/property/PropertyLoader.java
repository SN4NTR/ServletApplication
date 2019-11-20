package com.leverx.servletapp.property;

import org.hibernate.cfg.PropertyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static java.util.stream.Collectors.toMap;

public class PropertyLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyLoader.class.getSimpleName());

    private Map<String, String> propertiesMap;

    public void loadPropertiesFromFile(String fileName) {
        try (InputStream inputStream = PropertyHolder.class.getClassLoader().getResourceAsStream(fileName)) {
            var properties = new Properties();
            properties.load(inputStream);

            LOGGER.info("Properties have been loaded");

            setMapOfProperties(properties);
        } catch (IOException ex) {
            LOGGER.error("Properties can't be loaded");
            throw new InternalServerErrorException(ex);
        }
    }

    private void setMapOfProperties(Properties properties) {
        propertiesMap = properties.entrySet()
                .stream()
                .collect(toMap(
                        entry -> (String) entry.getKey(),
                        entry -> (String) entry.getValue()));

        LOGGER.info("propertiesMap is set");
    }

    public String getPropertyByKey(String key) {
        return propertiesMap.get(key);
    }
}

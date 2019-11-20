package com.leverx.servletapp.db;

import com.leverx.servletapp.property.PropertyLoader;

import static com.leverx.servletapp.db.constant.PropertyKey.DRIVER;
import static com.leverx.servletapp.db.constant.PropertyKey.FILE_NAME;
import static com.leverx.servletapp.db.constant.PropertyKey.PASSWORD;
import static com.leverx.servletapp.db.constant.PropertyKey.URL;
import static com.leverx.servletapp.db.constant.PropertyKey.USERNAME;

final class DBPropertyLoader {

    private PropertyLoader propertyLoader;

    DBPropertyLoader() {
        this.propertyLoader = new PropertyLoader();
        propertyLoader.loadPropertiesFromFile(FILE_NAME);
    }

    String getDriver() {
        return propertyLoader.getPropertyByKey(DRIVER);
    }

    String getUrl() {
        return propertyLoader.getPropertyByKey(URL);
    }

    String getUsername() {
        return propertyLoader.getPropertyByKey(USERNAME);
    }

    String getPassword() {
        return propertyLoader.getPropertyByKey(PASSWORD);
    }
}

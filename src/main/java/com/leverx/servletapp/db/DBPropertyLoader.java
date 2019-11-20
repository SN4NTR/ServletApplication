package com.leverx.servletapp.db;

import com.leverx.servletapp.property.PropertyLoader;

import static com.leverx.servletapp.db.constant.PropertyKey.DIALECT;
import static com.leverx.servletapp.db.constant.PropertyKey.DRIVER;
import static com.leverx.servletapp.db.constant.PropertyKey.FILE_NAME;
import static com.leverx.servletapp.db.constant.PropertyKey.PASSWORD;
import static com.leverx.servletapp.db.constant.PropertyKey.POOL_SIZE;
import static com.leverx.servletapp.db.constant.PropertyKey.SHOW_SQL;
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

    String getDialect() {
        return propertyLoader.getPropertyByKey(DIALECT);
    }

    String getPoolSize() {
        return propertyLoader.getPropertyByKey(POOL_SIZE);
    }

    String getShowSql() {
        return propertyLoader.getPropertyByKey(SHOW_SQL);
    }
}

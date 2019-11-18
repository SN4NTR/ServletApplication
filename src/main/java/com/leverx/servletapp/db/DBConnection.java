package com.leverx.servletapp.db;

import com.leverx.servletapp.db.constant.PropertyName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.leverx.servletapp.db.PropertyHolder.dbProperties;
import static com.leverx.servletapp.db.constant.PropertyName.DRIVER;
import static com.leverx.servletapp.db.constant.PropertyName.PASSWORD;
import static com.leverx.servletapp.db.constant.PropertyName.URL;
import static com.leverx.servletapp.db.constant.PropertyName.USERNAME;

class DBConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class.getSimpleName());

    static Connection createConnection() {
        LOGGER.info("Trying to create connection to database");

        final String DB_DRIVER = dbProperties.get(DRIVER.getValue());
        final String DB_URL = dbProperties.get(URL.getValue());
        final String DB_USERNAME = dbProperties.get(USERNAME.getValue());
        final String DB_PASSWORD = dbProperties.get(PASSWORD.getValue());

        try {
            Class.forName(DB_DRIVER);
            var connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            LOGGER.info("Connection has been created");

            return connection;
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}

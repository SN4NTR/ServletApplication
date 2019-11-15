package com.leverx.servletapp.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.leverx.servletapp.db.DBPropertyLoader.DB_DRIVER;
import static com.leverx.servletapp.db.DBPropertyLoader.DB_PASSWORD;
import static com.leverx.servletapp.db.DBPropertyLoader.DB_URL;
import static com.leverx.servletapp.db.DBPropertyLoader.DB_USERNAME;

class DBConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class.getSimpleName());

    static Connection createConnection() {
        LOGGER.info("Trying to create connection to database");

        try {
            Class.forName(DB_DRIVER);
            var connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            LOGGER.info("Connection has been created");

            return connection;
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException();
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException();
        }
    }
}

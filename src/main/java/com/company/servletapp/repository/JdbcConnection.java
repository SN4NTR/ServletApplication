package com.company.servletapp.repository;

import com.company.servletapp.logger.ServletLogger;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

final class JdbcConnection {

    private JdbcConnection() {
    }

    static Connection getConnection() {
        Connection connection = null;

        Logger logger = ServletLogger.getLogger();
        logger.info("Trying to create connection to database");

        final String FILE_NAME = "database.properties";

        try (InputStream inputStream = JdbcConnection.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            Properties properties = new Properties();

            if (inputStream != null) {
                properties.load(inputStream);
            }

            final String USER = properties.getProperty("db.user");
            final String PASSWORD = properties.getProperty("db.password");
            final String URL = properties.getProperty("db.url");
            final String DATASOURCE = properties.getProperty("db.datasource.driver-class-name");

            try {
                Class.forName(DATASOURCE);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Connection has been created");
            } catch (SQLException ex) {
                logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }

        return connection;
    }
}

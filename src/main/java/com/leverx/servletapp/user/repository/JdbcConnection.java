package com.leverx.servletapp.user.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JdbcConnection {

    private static final Logger logger = LoggerFactory.getLogger(JdbcConnection.class.getSimpleName());

    private static final String FILE_NAME = "database.properties";

    public static Connection getConnection() {
        Connection connection = null;

        logger.info("Trying to create connection to database");

        try (InputStream inputStream = JdbcConnection.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            Properties properties = new Properties();

            if (inputStream != null) {
                properties.load(inputStream);

                String username = properties.getProperty("db.user");
                String password = properties.getProperty("db.password");
                String url = properties.getProperty("db.url");
                String driver = properties.getProperty("db.datasource.driver-class-name");

                try {
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, username, password);
                    logger.info("Connection has been created");
                } catch (SQLException ex) {
                    logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }

        return connection;
    }
}

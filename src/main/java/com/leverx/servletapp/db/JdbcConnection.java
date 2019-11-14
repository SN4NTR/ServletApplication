package com.leverx.servletapp.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JdbcConnection {

    private static final Logger logger = LoggerFactory.getLogger(JdbcConnection.class.getSimpleName());

    public static Connection getConnection() {
        logger.info("Trying to create connection to database");

        var driverClassName = DataBaseProperties.driverClassName;
        var dataBaseUrl = DataBaseProperties.dataBaseUrl;
        var username = DataBaseProperties.username;
        var password = DataBaseProperties.password;

        try {
            Class.forName(driverClassName);
            Connection connection = DriverManager.getConnection(dataBaseUrl, username, password);

            logger.info("Connection has been created");

            return connection;
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());

            throw new InternalServerErrorException();
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());

            throw new InternalServerErrorException();
        }
    }
}

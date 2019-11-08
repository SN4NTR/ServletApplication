package com.company.servletapp.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

final class JdbcConnection {

    private static Connection connection;

    static Connection getConnection() {
        final String FILE_NAME = "application.properties";

        try (InputStream inputStream = JdbcConnection.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            Properties properties = new Properties();

            if (inputStream != null) {
                properties.load(inputStream);
            }

            final String USER = properties.getProperty("db.user");
            final String PASSWORD = properties.getProperty("db.password");
            final String URL = properties.getProperty("db.url");

            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException ex) {
                System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return connection;
    }
}

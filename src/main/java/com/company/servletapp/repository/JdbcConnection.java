package com.company.servletapp.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class JdbcConnection {

    private static JdbcConnection jdbcConnection = null;

    private JdbcConnection() {
    }

    static JdbcConnection getInstance() {
        return jdbcConnection == null ? new JdbcConnection() : jdbcConnection;
    }

    Connection create() {
        String URL = "jdbc:mysql://localhost:3306/servlet_db";
        String PASSWORD = "root";
        String USER = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

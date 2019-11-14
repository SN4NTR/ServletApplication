package com.leverx.servletapp.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class ConnectionPool implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class.getSimpleName());

    private static ConnectionPool connectionPool;

    private static final int MAX_CONNECTIONS = 10;
    private static final int ELEMENT_INDEX = 0;

    private final List<Connection> unusedConnections = Collections.synchronizedList(new LinkedList<>());
    private final List<Connection> usedConnections = Collections.synchronizedList(new LinkedList<>());

    private ConnectionPool() {
        while (unusedConnections.size() < MAX_CONNECTIONS) {
            Connection connection = createConnection();
            unusedConnections.add(connection);
        }
    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }

        return connectionPool;
    }

    private static Connection createConnection() {
        LOGGER.info("Trying to create connection to database");

        var driverClassName = DataBaseProperties.driverClassName;
        var dataBaseUrl = DataBaseProperties.dataBaseUrl;
        var username = DataBaseProperties.username;
        var password = DataBaseProperties.password;

        try {
            Class.forName(driverClassName);
            Connection connection = DriverManager.getConnection(dataBaseUrl, username, password);

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

    public Connection getConnection() {
        while (unusedConnections.isEmpty()) {
            LOGGER.info("All connections are busy");
        }

        Connection connection = unusedConnections.get(ELEMENT_INDEX);
        unusedConnections.remove(ELEMENT_INDEX);
        usedConnections.add(connection);

        return connection;
    }

    @Override
    public void close() {
        int lastElementIndex = usedConnections.size() - 1;
        Connection connection = usedConnections.get(lastElementIndex);
        usedConnections.remove(lastElementIndex);
        unusedConnections.add(connection);
    }
}

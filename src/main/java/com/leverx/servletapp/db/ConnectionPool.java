package com.leverx.servletapp.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.stream.Stream.generate;

public final class ConnectionPool {

    private static final int MAX_CONNECTIONS = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class.getSimpleName());
    private static final BlockingQueue<Connection> CONNECTION_BLOCKING_QUEUE = new ArrayBlockingQueue<>(MAX_CONNECTIONS);

    private static ConnectionPool connectionPool;
    private static DBPropertyLoader propertyLoader = new DBPropertyLoader();

    private ConnectionPool() {
        registerDriver();

        generate(ConnectionPool::createConnection)
                .limit(MAX_CONNECTIONS)
                .forEach(CONNECTION_BLOCKING_QUEUE::add);
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public Connection getConnection() {
        try {
            var connection = CONNECTION_BLOCKING_QUEUE.take();
            LOGGER.info("Connection is retrieved from queue");

            return connection;
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    public void releaseConnection(Connection connection) {
        try {
            CONNECTION_BLOCKING_QUEUE.put(connection);
            LOGGER.info("Connection is put back into queue");
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    private static void registerDriver() {
        try {
            var driver = propertyLoader.getDriver();

            Class.forName(driver);
            LOGGER.info("Driver is registered");
        } catch (ClassNotFoundException ex) {
            LOGGER.error("Driver can't be registered");
            throw new InternalServerErrorException(ex);
        }
    }

    private static Connection createConnection() {
        LOGGER.info("Trying to create connection to database");

        try {
            var url = propertyLoader.getUrl();
            var username = propertyLoader.getUsername();
            var password = propertyLoader.getPassword();

            var connection = DriverManager.getConnection(url, username, password);
            LOGGER.info("Connection has been created");

            return connection;
        } catch (SQLException ex) {
            LOGGER.error("Connection can't be created");
            throw new InternalServerErrorException(ex);
        }
    }
}

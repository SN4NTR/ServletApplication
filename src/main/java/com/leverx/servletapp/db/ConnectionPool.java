package com.leverx.servletapp.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.leverx.servletapp.db.PropertyHolder.dbProperties;
import static com.leverx.servletapp.db.constant.PropertyName.DRIVER;
import static com.leverx.servletapp.db.constant.PropertyName.PASSWORD;
import static com.leverx.servletapp.db.constant.PropertyName.URL;
import static com.leverx.servletapp.db.constant.PropertyName.USERNAME;

public final class ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class.getSimpleName());

    private static final int MAX_CONNECTIONS = 10;

    private static final BlockingQueue<Connection> CONNECTION_BLOCKING_QUEUE = new ArrayBlockingQueue<>(MAX_CONNECTIONS);

    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        registerDriver();

        while (CONNECTION_BLOCKING_QUEUE.size() < MAX_CONNECTIONS) {
            var connection = createConnection();
            CONNECTION_BLOCKING_QUEUE.add(connection);
        }
    }

    public static ConnectionPool getInstance() {
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

    public void closeConnection(Connection connection) {
        try {
            CONNECTION_BLOCKING_QUEUE.put(connection);
            LOGGER.info("Connection is put back into queue");
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    private static void registerDriver() {
        final String DB_DRIVER = dbProperties.get(DRIVER.getValue());

        try {
            Class.forName(DB_DRIVER);
            LOGGER.info("Driver is registered");
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    private Connection createConnection() {
        LOGGER.info("Trying to create connection to database");

        final String DB_URL = dbProperties.get(URL.getValue());
        final String DB_USERNAME = dbProperties.get(USERNAME.getValue());
        final String DB_PASSWORD = dbProperties.get(PASSWORD.getValue());

        try {
            var connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            LOGGER.info("Connection has been created");

            return connection;
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
}

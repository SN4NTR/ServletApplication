package com.leverx.servletapp.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class.getSimpleName());

    private static final int MAX_CONNECTIONS = 10;

    private static final BlockingQueue<Connection> CONNECTION_BLOCKING_QUEUE = new ArrayBlockingQueue<>(MAX_CONNECTIONS);

    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        while (CONNECTION_BLOCKING_QUEUE.size() < MAX_CONNECTIONS) {
            var connection = DBConnection.createConnection();
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
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }

        LOGGER.info("Connection is put back into queue");
    }
}

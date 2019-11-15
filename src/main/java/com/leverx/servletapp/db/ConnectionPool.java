package com.leverx.servletapp.db;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.leverx.servletapp.db.DBConnection.createConnection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionPool implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class.getSimpleName());

    private static ConnectionPool connectionPool;

    private static final int MAX_CONNECTIONS = 10;
    private static final int ELEMENT_INDEX = 0;

    private static final List<Connection> UNUSED_CONNECTIONS = Collections.synchronizedList(new LinkedList<>());
    private static final List<Connection> USED_CONNECTIONS = Collections.synchronizedList(new LinkedList<>());

    static {
        while (UNUSED_CONNECTIONS.size() < MAX_CONNECTIONS) {
            var connection = createConnection();
            UNUSED_CONNECTIONS.add(connection);
        }
    }

    @Override
    public void close() {
        var lastElementIndex = USED_CONNECTIONS.size() - 1;
        var connection = USED_CONNECTIONS.get(lastElementIndex);
        USED_CONNECTIONS.remove(lastElementIndex);
        UNUSED_CONNECTIONS.add(connection);
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }

        return connectionPool;
    }

    public Connection getConnection() {
        while (UNUSED_CONNECTIONS.isEmpty()) {
            LOGGER.info("All connections are busy");
        }

        var connection = UNUSED_CONNECTIONS.get(ELEMENT_INDEX);
        UNUSED_CONNECTIONS.remove(ELEMENT_INDEX);
        USED_CONNECTIONS.add(connection);

        return connection;
    }
}

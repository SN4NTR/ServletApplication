package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.db.ConnectionPool;
import com.leverx.servletapp.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.db.ConnectionPool.getInstance;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.DELETE;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.INSERT;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_ALL;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_BY_ID;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_BY_NAME;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.UPDATE;
import static com.leverx.servletapp.user.repository.constant.UsersFields.FIRST_NAME;
import static com.leverx.servletapp.user.repository.constant.UsersFields.ID;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class.getSimpleName());

    @Override
    public void save(User user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());

        ConnectionPool connectionPool = getInstance();
        Connection connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(INSERT)) {

            var firstName = user.getFirstName();
            preparedStatement.setString(1, firstName);
            preparedStatement.executeUpdate();

            LOGGER.info("User has been saved");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);

        ConnectionPool connectionPool = getInstance();
        Connection connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public Collection<User> findByName(String name) {
        LOGGER.info("Getting user by firstName = {}", name);

        ConnectionPool connectionPool = getInstance();
        Connection connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {

            preparedStatement.setString(1, name);

            try (var resultSet = preparedStatement.executeQuery()) {
                return getListOfUsersFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");

        ConnectionPool connectionPool = getInstance();
        Connection connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(SELECT_ALL);
             var resultSet = preparedStatement.executeQuery()) {

            return getListOfUsersFromResultSet(resultSet);
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting user with id = {}", id);

        ConnectionPool connectionPool = getInstance();
        Connection connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            LOGGER.info("User has been deleted");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public void update(User user) {
        LOGGER.info("Updating user with id = {}", user.getId());

        ConnectionPool connectionPool = getInstance();
        Connection connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(UPDATE)) {

            var firstName = user.getFirstName();
            var id = user.getId();

            preparedStatement.setString(1, firstName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            LOGGER.info("User has been updated");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    private List<User> getListOfUsersFromResultSet(ResultSet resultSet) throws SQLException {
        var users = new ArrayList<User>();

        while (resultSet.next()) {
            resultSet.previous();

            var user = getUserFromResultSet(resultSet);
            users.add(user);
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.next();
        var id = resultSet.getInt(ID);
        var firstName = resultSet.getString(FIRST_NAME);

        LOGGER.info("User with id = {} has been found", id);

        return new User(id, firstName);
    }
}

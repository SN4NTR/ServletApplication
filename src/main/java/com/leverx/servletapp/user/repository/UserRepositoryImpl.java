package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.db.HibernateConfig;
import com.leverx.servletapp.user.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.db.ConnectionPool.getInstance;
import static com.leverx.servletapp.db.HibernateConfig.getSessionFactory;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.DELETE;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_ALL;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_BY_ID;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_BY_NAME;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.UPDATE;
import static com.leverx.servletapp.user.repository.constant.UsersFields.FIRST_NAME;
import static com.leverx.servletapp.user.repository.constant.UsersFields.ID;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class.getSimpleName());
    private static final int ID_INDEX = 1;
    private static final int ID_INDEX_FOR_UPDATE = 2;
    private static final int FIRST_NAME_INDEX = 1;

    @Override
    public void save(User user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());

        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.save(user);
        session.close();
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);

        var connectionPool = getInstance();
        var connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(ID_INDEX, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Collection<User> findByName(String name) {
        LOGGER.info("Getting user by firstName = {}", name);

        var connectionPool = getInstance();
        var connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {

            preparedStatement.setString(FIRST_NAME_INDEX, name);

            try (var resultSet = preparedStatement.executeQuery()) {
                return getListOfUsersFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");

        var connectionPool = getInstance();
        var connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(SELECT_ALL);
             var resultSet = preparedStatement.executeQuery()) {

            return getListOfUsersFromResultSet(resultSet);
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting user with id = {}", id);

        var connectionPool = getInstance();
        var connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(ID_INDEX, id);
            preparedStatement.executeUpdate();

            LOGGER.info("User has been deleted");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void update(User user) {
        LOGGER.info("Updating user with id = {}", user.getId());

        var connectionPool = getInstance();
        var connection = connectionPool.getConnection();

        try (var preparedStatement = connection.prepareStatement(UPDATE)) {

            var firstName = user.getFirstName();
            var id = user.getId();

            preparedStatement.setString(FIRST_NAME_INDEX, firstName);
            preparedStatement.setInt(ID_INDEX_FOR_UPDATE, id);
            preparedStatement.executeUpdate();

            LOGGER.info("User has been updated");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex);
        } finally {
            connectionPool.releaseConnection(connection);
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

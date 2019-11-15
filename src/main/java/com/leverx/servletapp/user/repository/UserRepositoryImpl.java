package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.db.ConnectionPool;
import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.user.entity.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.user.repository.constant.SQLQuery.DELETE;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.INSERT;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_ALL;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_BY_NAME;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.SELECT_BY_ID;
import static com.leverx.servletapp.user.repository.constant.SQLQuery.UPDATE;
import static com.leverx.servletapp.user.repository.constant.UsersColumns.FIRST_NAME;
import static com.leverx.servletapp.user.repository.constant.UsersColumns.ID;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class.getSimpleName());

    @Override
    public void save(UserDto user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());

        try (var connectionPool = new ConnectionPool();
             var preparedStatement = connectionPool.getConnection().prepareStatement(INSERT)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.executeUpdate();

            LOGGER.info("User has been saved");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex.getCause());
        }
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);

        try (var connectionPool = new ConnectionPool();
             var preparedStatement = connectionPool.getConnection().prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex.getCause());
        }
    }

    @Override
    public Collection<User> findByName(String name) {
        LOGGER.info("Getting user by firstName = {}", name);

        try (var connectionPool = new ConnectionPool();
             var preparedStatement = connectionPool.getConnection().prepareStatement(SELECT_BY_NAME)) {

            preparedStatement.setString(1, name);

            try (var resultSet = preparedStatement.executeQuery()) {
                return getListOfUsersFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex.getCause());
        }
    }

    @Override
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");

        try (var connectionPool = new ConnectionPool();
             var preparedStatement = connectionPool.getConnection().prepareStatement(SELECT_ALL);
             var resultSet = preparedStatement.executeQuery()) {

            return getListOfUsersFromResultSet(resultSet);
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex.getCause());
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting user with id = {}", id);

        try (var connectionPool = new ConnectionPool();
             var preparedStatement = connectionPool.getConnection().prepareStatement(DELETE)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            LOGGER.info("User has been deleted");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex.getCause());
        }
    }

    @Override
    public void update(int id, UserDto user) {
        LOGGER.info("Updating user with id = {}", id);

        try (var connectionPool = new ConnectionPool();
             var preparedStatement = connectionPool.getConnection().prepareStatement(UPDATE)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            LOGGER.info("User has been updated");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
            throw new InternalServerErrorException(ex.getCause());
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

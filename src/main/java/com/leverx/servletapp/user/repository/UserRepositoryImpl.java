package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.database.JdbcConnection;
import com.leverx.servletapp.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.leverx.servletapp.user.constant.SQL.DELETE;
import static com.leverx.servletapp.user.constant.SQL.FIRST_NAME;
import static com.leverx.servletapp.user.constant.SQL.ID;
import static com.leverx.servletapp.user.constant.SQL.INSERT;
import static com.leverx.servletapp.user.constant.SQL.SELECT_ALL;
import static com.leverx.servletapp.user.constant.SQL.SELECT_BY_ID;
import static com.leverx.servletapp.user.constant.SQL.UPDATE;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class.getSimpleName());

    @Override
    public void save(User user) {
        LOGGER.info("Saving user with name '{}'.", user.getFirstName());

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.executeUpdate();

            LOGGER.info("User has been saved");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());

            throw new InternalServerErrorException();
        }
    }

    @Override
    public User findById(int id) {
        LOGGER.info("Getting user by id = {}", id);

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());

            throw new InternalServerErrorException();
        }
    }

    @Override
    public Collection<User> findAll() {
        LOGGER.info("Getting all users");

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                var user = getUserFromResultSet(resultSet);
                users.add(user);
            }

            return users;
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());

            throw new InternalServerErrorException();
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting user with id = {}", id);

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            LOGGER.info("User has been deleted");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());

            throw new InternalServerErrorException();
        }
    }

    @Override
    public void update(User user) {
        LOGGER.info("Updating user with id = {}", user.getId());

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();

            LOGGER.info("User has been updated");
        } catch (SQLException ex) {
            LOGGER.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());

            throw new InternalServerErrorException();
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt(ID);
        var firstName = resultSet.getString(FIRST_NAME);

        LOGGER.info("User with id = {} has been found", id);

        return new User(id, firstName);
    }
}

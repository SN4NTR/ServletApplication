package com.leverx.servletapp.user.repository;

import com.leverx.servletapp.user.entity.User;
import com.leverx.servletapp.util.JdbcConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class.getSimpleName());

    private static final String TABLE_NAME = "users";
    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "first_name";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + FIRST_NAME_COLUMN + ") VALUES (?)";
    private static final String SQL_SELECT_BY_FIRST_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIRST_NAME_COLUMN + " = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " = ?";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + FIRST_NAME_COLUMN + " = ? WHERE " + ID_COLUMN + " = ?";

    @Override
    public void save(User user) {
        logger.info("Saving user with name '{}'.", user.getFirstName());

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_INSERT) : null) {

            if (preparedStatement != null) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.executeUpdate();
            }

            logger.info("User has been saved");
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        }
    }

    @Override
    public Collection<User> findByFirstName(String firstName) {
        List<User> users = new ArrayList<>();

        logger.info("Getting user by firstName = {}", firstName);

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT_BY_FIRST_NAME) : null) {

            if (preparedStatement != null) {
                preparedStatement.setString(1, firstName);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet != null && resultSet.next()) {
                        String userId = resultSet.getString(ID_COLUMN);
                        String userFirstName = resultSet.getString(FIRST_NAME_COLUMN);

                        User user = new User();
                        user.setFirstName(userFirstName);
                        user.setId(Integer.parseInt(userId));

                        users.add(user);
                    }
                }

                logger.info("User has been found");
            }
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        }

        return users;
    }

    @Override
    public User findById(int id) {
        User user = new User();

        logger.info("Getting user by id = {}", id);

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT_BY_ID) : null) {

            if (preparedStatement != null) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet != null && resultSet.next()) {
                        String userId = resultSet.getString(ID_COLUMN);
                        String userFirstName = resultSet.getString(FIRST_NAME_COLUMN);

                        user.setId(Integer.parseInt(userId));
                        user.setFirstName(userFirstName);
                    }

                    logger.info("User has been found");
                }
            }
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        }

        return user;
    }

    @Override
    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();

        logger.info("Getting all users");

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT_ALL) : null;
             ResultSet resultSet = preparedStatement != null ? preparedStatement.executeQuery() : null) {

            while (resultSet != null && resultSet.next()) {
                String id = resultSet.getString(ID_COLUMN);
                String firstName = resultSet.getString(FIRST_NAME_COLUMN);

                User user = new User();
                user.setId(Integer.parseInt(id));
                user.setFirstName(firstName);

                users.add(user);
            }

            logger.info("Users are got");
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        }

        return users;
    }

    @Override
    public void delete(int id) {
        logger.info("Deleting user with id = {}", id);

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_DELETE) : null) {

            if (preparedStatement != null) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }

            logger.info("User has been deleted");
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        }
    }

    @Override
    public void update(User user) {
        logger.info("Updating user with id = {}", user.getId());

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_UPDATE) : null) {

            if (preparedStatement != null) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setInt(2, user.getId());
                preparedStatement.executeUpdate();
            }

            logger.info("User has been updated");
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        }
    }
}

package com.company.servletapp.repository;

import com.company.servletapp.entity.User;
import com.company.servletapp.logger.ServletLogger;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final Logger logger;

    public UserRepositoryImpl() {
        this.logger = ServletLogger.getLogger();
    }

    @Override
    public void save(User user) {
        final String SQL_INSERT = "INSERT INTO users (first_name) VALUES (?)";

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
    public List<User> getByFirstName(String firstName) {
        final String SQL_SELECT_BY_FIRST_NAME = "SELECT * FROM users WHERE first_name = ?";

        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;

        logger.info("Getting user by firstName = {}", firstName);

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT_BY_FIRST_NAME) : null) {

            if (preparedStatement != null) {
                preparedStatement.setString(1, firstName);
                resultSet = preparedStatement.executeQuery();

                while (resultSet != null && resultSet.next()) {
                    String userId = resultSet.getString("id");
                    String userFirstName = resultSet.getString("first_name");

                    User user = new User();
                    user.setFirstName(userFirstName);
                    user.setId(Integer.parseInt(userId));
                    users.add(user);
                }
            }

            logger.info("User has been found");
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return users;
    }

    @Override
    public User getById(int id) {
        final String SQL_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";

        User user = new User();
        ResultSet resultSet = null;

        logger.info("Getting user by id = {}", id);

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT_BY_ID) : null) {

            if (preparedStatement != null) {
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();

                while (resultSet != null && resultSet.next()) {
                    String userId = resultSet.getString("id");
                    String userFirstName = resultSet.getString("first_name");

                    user.setId(Integer.parseInt(userId));
                    user.setFirstName(userFirstName);
                }
            }

            logger.info("User has been found");
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        final String SQL_SELECT = "SELECT * FROM users";

        List<User> users = new ArrayList<>();
        ResultSet resultSet = null;

        logger.info("Getting all users");

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT) : null) {

            if (preparedStatement != null) {
                resultSet = preparedStatement.executeQuery();

                while (resultSet != null && resultSet.next()) {
                    String id = resultSet.getString("id");
                    String firstName = resultSet.getString("first_name");

                    User user = new User();
                    user.setId(Integer.parseInt(id));
                    user.setFirstName(firstName);

                    users.add(user);
                }
            }

            logger.info("Users are got");
        } catch (SQLException ex) {
            logger.error("SQL State: {}\n{}", ex.getSQLState(), ex.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return users;
    }

    @Override
    public void delete(int id) {
        final String SQL_DELETE = "DELETE FROM users WHERE id = ?";

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
        final String SQL_UPDATE = "UPDATE users SET first_name = ? WHERE id = ?";

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

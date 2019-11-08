package com.company.servletapp.repository;

import com.company.servletapp.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(User user) {
        Connection connection = JdbcConnection.getConnection();

        String SQL_INSERT = "INSERT INTO users (first_name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_INSERT) : null) {
            if (preparedStatement != null) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {;
            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<User> getByFirstName(String firstName) {
        Connection connection = JdbcConnection.getConnection();

        List<User> users = new ArrayList<>();
        String SQL_SELECT_BY_FIRST_NAME = "SELECT * FROM users WHERE first_name = ?";

        try (PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT_BY_FIRST_NAME) : null) {
            if (preparedStatement != null) {
                preparedStatement.setString(1, firstName);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet != null && resultSet.next()) {
                    String userId = resultSet.getString("id");
                    String userFirstName = resultSet.getString("first_name");

                    User user = new User();
                    user.setFirstName(userFirstName);
                    user.setId(Integer.parseInt(userId));
                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return users;
    }

    @Override
    public User getById(int id) {
        Connection connection = JdbcConnection.getConnection();

        User user = new User();
        String SQL_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT_BY_ID) : null) {
            if (preparedStatement != null) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet != null && resultSet.next()) {
                    String userId = resultSet.getString("id");
                    String userFirstName = resultSet.getString("first_name");

                    user.setId(Integer.parseInt(userId));
                    user.setFirstName(userFirstName);
                }
            }
        } catch (SQLException ex) {
            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        Connection connection = JdbcConnection.getConnection();

        List<User> users = new ArrayList<>();
        String SQL_SELECT = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT) : null) {
            ResultSet resultSet = preparedStatement != null ? preparedStatement.executeQuery() : null;

            while (resultSet != null && resultSet.next()) {
                String id = resultSet.getString("id");
                String firstName = resultSet.getString("first_name");

                User user = new User();
                user.setId(Integer.parseInt(id));
                user.setFirstName(firstName);

                users.add(user);
            }
        } catch (SQLException ex) {;
            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return users;
    }

    @Override
    public void delete(int id) {
        Connection connection = JdbcConnection.getConnection();

        final String SQL_DELETE = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_DELETE) : null) {
            if (preparedStatement != null) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {;
            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

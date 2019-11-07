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

        String SQL_INSERT = "INSERT INTO users VALUES (?)";

        try (PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_INSERT) : null) {
            if (preparedStatement != null) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {;
            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
        }
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        Connection connection = JdbcConnection.getConnection();

        List<User> users = new ArrayList<>();
        String SQL_SELECT = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = connection != null ? connection.prepareStatement(SQL_SELECT) : null) {
            ResultSet resultSet = preparedStatement != null ? preparedStatement.executeQuery() : null;

            while (resultSet != null && resultSet.next()) {
                String firstName = resultSet.getString("first_name");

                User user = new User();
                user.setFirstName(firstName);

                users.add(user);
            }

            return users;
        } catch (SQLException ex) {;
            System.err.format("SQL State: %s\n%s", ex.getSQLState(), ex.getMessage());
            return null;
        }
    }
}
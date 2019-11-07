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

    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        Connection connection = JdbcConnection.getInstance().create();

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
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

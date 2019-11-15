package com.leverx.servletapp.user.repository.constant;

public class SQLQuery {

    public static final String INSERT = "INSERT INTO users (first_name) VALUES (?)";
    public static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String SELECT_BY_NAME = "SELECT * FROM users WHERE first_name = ?";
    public static final String SELECT_ALL = "SELECT * FROM users";
    public static final String DELETE = "DELETE FROM users WHERE id = ?";
    public static final String UPDATE = "UPDATE users SET first_name = ? WHERE id = ?";
}

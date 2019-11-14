package com.leverx.servletapp.user.constant;

public class SQL {

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";

    public static final String INSERT = "INSERT INTO users (" + FIRST_NAME + ") VALUES (?)";
    public static final String SELECT_BY_ID = "SELECT * FROM users WHERE " + ID + " = ?";
    public static final String SELECT_ALL = "SELECT * FROM users";
    public static final String DELETE = "DELETE FROM users WHERE " + ID + " = ?";
    public static final String UPDATE = "UPDATE users SET " + FIRST_NAME + " = ? WHERE " + ID + " = ?";
}

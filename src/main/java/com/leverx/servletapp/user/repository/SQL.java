package com.leverx.servletapp.user.repository;

class SQL {

    static final String ID = "id";
    static final String FIRST_NAME = "first_name";

    static final String INSERT = "INSERT INTO users (" + FIRST_NAME + ") VALUES (?)";
    static final String SELECT_BY_FIRST_NAME = "SELECT * FROM users WHERE " + FIRST_NAME + " = ?";
    static final String SELECT_BY_ID = "SELECT * FROM users WHERE " + ID + " = ?";
    static final String SELECT_ALL = "SELECT * FROM users";
    static final String DELETE = "DELETE FROM users WHERE " + ID + " = ?";
    static final String UPDATE = "UPDATE users SET " + FIRST_NAME + " = ? WHERE " + ID + " = ?";
}

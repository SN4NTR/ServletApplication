package com.leverx.servletapp.db.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyName {

    FILE_NAME("database.properties"),
    DRIVER("driver"),
    URL("url"),
    USERNAME("username"),
    PASSWORD("password");

    private String value;
}

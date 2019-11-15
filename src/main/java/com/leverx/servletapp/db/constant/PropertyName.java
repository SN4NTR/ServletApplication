package com.leverx.servletapp.db.constant;

import lombok.Getter;

@Getter
public enum PropertyName {

    FILE_NAME("database.properties"),
    DRIVER("db.datasource.driver-class-name"),
    URL("db.url"),
    USERNAME("db.user"),
    PASSWORD("db.password");

    private String value;

    PropertyName(String value) {
        this.value = value;
    }
}

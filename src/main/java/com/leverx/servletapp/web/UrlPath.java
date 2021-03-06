package com.leverx.servletapp.web;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UrlPath {

    public static final String DELIMITER = "/";
    public static final String CATS_ENDPOINT = "cats";
    public static final String DOGS_ENDPOINT = "dogs";
    public static final String USERS_ENDPOINT = "users";
    public static final String ANIMALS_ENDPOINT = "animals";
}

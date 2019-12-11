package com.leverx.servletapp.util.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UrlComponent {

    public static final String DELIMITER = "/";
    public static final String CATS_ENDPOINT = "cats";
    public static final String DOGS_ENDPOINT = "dogs";
    public static final String USERS_ENDPOINT = "users";
    public static final String ANIMALS_ENDPOINT = "animals";

    public static final int LAST_ELEMENT_POSITION = 1;
    public static final int PENULTIMATE_ELEMENT_POSITION = 2;
}

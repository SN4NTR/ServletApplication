package com.leverx.servletapp.user.validator;

public class UserValidator {

    private static final int FIRST_NAME_LENGTH = 60;

    public static boolean isFirstNameLengthValid(String firstName) {
        return firstName.length() <= FIRST_NAME_LENGTH;
    }
}

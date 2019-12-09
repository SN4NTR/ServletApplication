package com.leverx.servletapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}

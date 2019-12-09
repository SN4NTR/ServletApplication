package com.leverx.servletapp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message) {
        super(message);
    }
}

package com.leverx.servletapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidationException extends Exception {

    private int responseStatus;

    public ValidationException(String message, int responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }
}

package com.leverx.servletapp.core.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValidationException extends Exception {

    private int responseStatus;
    private String message;

    public ValidationException(String message, int responseStatus) {
        super(message);
        this.message = message;
        this.responseStatus = responseStatus;
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}

package com.leverx.servletapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InternalServerErrorException extends RuntimeException {

    private int responseStatus;
    private String message;

    public InternalServerErrorException(String message, int responseStatus) {
        super(message);
        this.message = message;
        this.responseStatus = responseStatus;
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}

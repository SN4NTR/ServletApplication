package com.leverx.servletapp.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

    private int responseStatus;

    public InternalServerErrorException(String message, int responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }
}

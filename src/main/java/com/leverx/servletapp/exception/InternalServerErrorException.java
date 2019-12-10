package com.leverx.servletapp.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

    private int responseStatus;

    public InternalServerErrorException(Exception ex, int responseStatus) {
        super(ex);
        this.responseStatus = responseStatus;
    }
}

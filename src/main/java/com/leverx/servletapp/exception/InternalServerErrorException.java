package com.leverx.servletapp.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(Exception ex) {
        super(ex);
    }
}

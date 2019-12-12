package com.leverx.servletapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InternalServerErrorException extends RuntimeException {

    private int responseStatus;

    public InternalServerErrorException(String message, int responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }
}

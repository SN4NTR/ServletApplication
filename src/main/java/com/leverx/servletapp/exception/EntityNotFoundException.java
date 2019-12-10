package com.leverx.servletapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntityNotFoundException extends Exception {

    private int responseStatus;

    public EntityNotFoundException(String message, int responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }
}

package com.leverx.servletapp.user.dto;

import lombok.Data;

import javax.validation.constraints.Size;

import static com.leverx.servletapp.validator.EntityValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.validator.EntityValidator.NAME_MIN_SIZE;

@Data
public class UserInputDto {

    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    private String firstName;
}

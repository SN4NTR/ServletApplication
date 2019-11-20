package com.leverx.servletapp.user.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotNull
    @Size(min = 5, max = 60)
    private String firstName;
}

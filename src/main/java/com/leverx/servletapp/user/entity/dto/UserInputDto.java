package com.leverx.servletapp.user.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserInputDto {

    @Size(min = 5, max = 60)
    private String firstName;

    @JsonIgnore
    public static final int FIRST_NAME_LENGTH_MIN = 5;

    @JsonIgnore
    public static final int FIRST_NAME_LENGTH_MAX = 60;
}

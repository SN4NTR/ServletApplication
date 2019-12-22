package com.leverx.servletapp.model.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.leverx.servletapp.model.user.validator.UserValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.model.user.validator.UserValidator.NAME_MIN_SIZE;
import static java.util.Collections.emptyList;

@Data
public class UserInputDto {

    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    private String firstName;

    @Email
    @NotNull
    private String email;

    private int animalPoints;

    private List<Integer> catsIds = emptyList();
    private List<Integer> dogsIds = emptyList();
}

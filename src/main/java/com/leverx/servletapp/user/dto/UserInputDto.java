package com.leverx.servletapp.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.leverx.servletapp.user.validator.UserValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.user.validator.UserValidator.NAME_MIN_SIZE;
import static com.leverx.servletapp.user.validator.UserValidator.WRONG_NAME_SIZE_MSG;
import static java.util.Collections.emptyList;

@Data
public class UserInputDto {

    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = WRONG_NAME_SIZE_MSG)
    private String firstName;

    private List<Integer> catIds = emptyList();
}

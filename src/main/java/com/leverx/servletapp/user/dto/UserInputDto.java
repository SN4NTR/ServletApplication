package com.leverx.servletapp.user.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

import static com.leverx.servletapp.validator.EntityValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.validator.EntityValidator.NAME_MIN_SIZE;
import static java.util.Collections.emptyList;

@Data
public class UserInputDto {

    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    private String firstName;

    private List<Integer> catIds = emptyList();
}

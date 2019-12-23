package com.leverx.servletapp.user.dto;

import lombok.Data;

import javax.validation.constraints.Min;

import static com.leverx.servletapp.user.validator.UserValidator.ANIMAL_POINTS_MIN;

@Data
public class UserTransferDto {

    private int receiverId;

    @Min(value = ANIMAL_POINTS_MIN)
    private int animalPoints;
}

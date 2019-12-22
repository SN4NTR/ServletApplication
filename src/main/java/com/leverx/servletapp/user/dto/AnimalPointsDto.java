package com.leverx.servletapp.user.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.leverx.servletapp.user.dto.validator.AnimalPointsValidator.ANIMAL_POINTS_MIN;

@Getter
public class AnimalPointsDto {

    private int receiverId;

    @Min(value = ANIMAL_POINTS_MIN)
    private int animalPoints;
}

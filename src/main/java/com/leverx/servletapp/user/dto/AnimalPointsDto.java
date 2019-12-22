package com.leverx.servletapp.user.dto;

import lombok.Getter;

import javax.validation.constraints.Min;

import static com.leverx.servletapp.user.dto.validator.AnimalPointsValidator.ANIMAL_POINTS_MIN;

@Getter
// TODO create animal points transfer
public class AnimalPointsDto {

    private int receiverId;

    @Min(value = ANIMAL_POINTS_MIN)
    private int animalPoints;
}

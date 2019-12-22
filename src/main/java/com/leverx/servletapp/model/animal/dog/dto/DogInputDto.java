package com.leverx.servletapp.model.animal.dog.dto;

import com.leverx.servletapp.model.animal.parent.dto.AnimalInputDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.MIN_VALUE;

@Getter
@Setter
public class DogInputDto extends AnimalInputDto {

    @NotNull
    @Min(value = MIN_VALUE)
    private int goodBoyAmount;
}

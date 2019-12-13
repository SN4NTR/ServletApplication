package com.leverx.servletapp.model.animal.dog.dto;

import com.leverx.servletapp.model.animal.parent.dto.AnimalInputDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.MIN_VALUE;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.WRONG_VALUE;

@Getter
@Setter
public class DogInputDto extends AnimalInputDto {

    @Min(value = MIN_VALUE, message = WRONG_VALUE)
    private int goodBoyAmount;
}

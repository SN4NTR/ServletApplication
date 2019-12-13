package com.leverx.servletapp.model.animal.cat.dto;

import com.leverx.servletapp.model.animal.parent.dto.AnimalInputDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.MIN_VALUE;
import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.WRONG_VALUE;

@Getter
@Setter
public class CatInputDto extends AnimalInputDto {

    @Min(value = MIN_VALUE, message = WRONG_VALUE)
    private int miceCaught;
}

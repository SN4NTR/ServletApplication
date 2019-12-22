package com.leverx.servletapp.cat.dto;

import com.leverx.servletapp.animal.dto.AnimalInputDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

import static com.leverx.servletapp.cat.validator.CatValidator.MIN_VALUE;

@Getter
@Setter
public class CatInputDto extends AnimalInputDto {

    @NotNull
    @Min(value = MIN_VALUE)
    private int miceCaught;
}

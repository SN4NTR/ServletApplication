package com.leverx.servletapp.cat.dto;

import com.leverx.servletapp.animal.dto.AnimalOutputDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.leverx.servletapp.cat.validator.CatValidator.MIN_VALUE;

@Getter
@Setter
public class CatOutputDto extends AnimalOutputDto {

    @NotNull
    @Min(value = MIN_VALUE)
    private int miceCaught;

    public CatOutputDto(int id, String name, LocalDate dateOfBirth, int miceCaught) {
        super(id, name, dateOfBirth);
        this.miceCaught = miceCaught;
    }
}

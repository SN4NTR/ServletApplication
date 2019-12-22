package com.leverx.servletapp.model.animal.cat.dto;

import com.leverx.servletapp.model.animal.parent.dto.AnimalOutputDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.MIN_VALUE;

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

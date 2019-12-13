package com.leverx.servletapp.model.animal.dog.dto;

import com.leverx.servletapp.model.animal.parent.dto.AnimalOutputDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDate;

import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.MIN_VALUE;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.WRONG_VALUE;

@Getter
@Setter
public class DogOutputDto extends AnimalOutputDto {

    @Min(value = MIN_VALUE, message = WRONG_VALUE)
    private int goodBoyAmount;

    public DogOutputDto(int id, String name, LocalDate dateOfBirth, int goodBoyAmount) {
        super(id, name, dateOfBirth);
        this.goodBoyAmount = goodBoyAmount;
    }
}

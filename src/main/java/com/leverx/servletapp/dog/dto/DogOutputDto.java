package com.leverx.servletapp.dog.dto;

import com.leverx.servletapp.animal.dto.AnimalOutputDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDate;

import static com.leverx.servletapp.dog.validator.DogValidator.MIN_VALUE;

@Getter
@Setter
public class DogOutputDto extends AnimalOutputDto {

    @Min(value = MIN_VALUE)
    private int goodBoyAmount;

    public DogOutputDto(int id, String name, LocalDate dateOfBirth, int goodBoyAmount) {
        super(id, name, dateOfBirth);
        this.goodBoyAmount = goodBoyAmount;
    }
}

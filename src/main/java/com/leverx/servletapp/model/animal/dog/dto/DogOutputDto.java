package com.leverx.servletapp.model.animal.dog.dto;

import com.leverx.servletapp.model.animal.parent.dto.AnimalOutputDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DogOutputDto extends AnimalOutputDto {

    public DogOutputDto(int id, String name, LocalDate dateOfBirth) {
        setId(id);
        setName(name);
        setDateOfBirth(dateOfBirth);
    }
}

package com.leverx.servletapp.model.animal.dog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.leverx.servletapp.model.user.dto.UserOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.MIN_VALUE;
import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.NAME_MIN_SIZE;

@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public class DogWithOwnerDto {

    private int id;

    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    private String name;

    @Min(value = MIN_VALUE)
    private int goodBoyAmount;

    @PastOrPresent
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    private List<UserOutputDto> owners;
}

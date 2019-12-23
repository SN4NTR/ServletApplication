package com.leverx.servletapp.animal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.leverx.servletapp.user.dto.UserOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.leverx.servletapp.animal.validator.AnimalValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.animal.validator.AnimalValidator.NAME_MIN_SIZE;

@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public class AnimalWithOwnerDto {

    private int id;

    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    private String name;

    @NotNull
    @PastOrPresent
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    private List<UserOutputDto> owners;
}

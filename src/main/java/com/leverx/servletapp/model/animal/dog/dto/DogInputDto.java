package com.leverx.servletapp.model.animal.dog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.NAME_MIN_SIZE;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.WRONG_DATE_MSG;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.WRONG_NAME_SIZE_MSG;

@Data
public class DogInputDto {

    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = WRONG_NAME_SIZE_MSG)
    private String name;

    @NotNull
    @PastOrPresent(message = WRONG_DATE_MSG)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;
}

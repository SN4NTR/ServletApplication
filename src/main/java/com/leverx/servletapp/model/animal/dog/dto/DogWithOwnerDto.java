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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.NAME_MIN_SIZE;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.WRONG_DATE_MSG;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.WRONG_NAME_SIZE_MSG;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public class DogWithOwnerDto {

    @NonNull
    private int id;

    @NonNull
    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = WRONG_NAME_SIZE_MSG)
    private String name;

    @NonNull
    @NotNull
    @PastOrPresent(message = WRONG_DATE_MSG)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    private UserOutputDto owner;
}

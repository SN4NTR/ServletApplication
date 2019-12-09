package com.leverx.servletapp.cat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.leverx.servletapp.cat.validator.CatValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.cat.validator.CatValidator.NAME_MIN_SIZE;
import static com.leverx.servletapp.cat.validator.CatValidator.WRONG_DATE_MSG;
import static com.leverx.servletapp.cat.validator.CatValidator.WRONG_NAME_SIZE_MSG;

@Getter
@Setter
@AllArgsConstructor
public class CatOutputDto {

    private int id;

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

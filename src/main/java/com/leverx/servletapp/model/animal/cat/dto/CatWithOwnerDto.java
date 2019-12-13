package com.leverx.servletapp.model.animal.cat.dto;

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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.MIN_VALUE;
import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.WRONG_VALUE;
import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.NAME_MIN_SIZE;
import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.WRONG_DATE_MSG;
import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.WRONG_NAME_SIZE_MSG;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public class CatWithOwnerDto {

    @NonNull
    private int id;

    @NonNull
    @NotNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = WRONG_NAME_SIZE_MSG)
    private String name;

    @NonNull
    @Min(value = MIN_VALUE, message = WRONG_VALUE)
    private int miceCaught;

    @NonNull
    @NotNull
    @PastOrPresent(message = WRONG_DATE_MSG)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    private List<UserOutputDto> owners;
}

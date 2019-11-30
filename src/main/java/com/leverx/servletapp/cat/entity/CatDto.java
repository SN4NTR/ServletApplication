package com.leverx.servletapp.cat.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class CatDto {

    private List<Integer> idList;

    @NotNull
    @Size(min = 5, max = 60)
    private String name;

    @NotNull
    @PastOrPresent
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;
}

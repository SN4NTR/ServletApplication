package com.leverx.servletapp.user.entity.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.leverx.servletapp.cat.entity.dto.CatOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static java.util.Collections.emptyList;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public class UserWithCatsDto {

    private int id;

    @NotNull
    @Size(min = 5, max = 60)
    private String firstName;

    private List<CatOutputDto> cats = emptyList();
}

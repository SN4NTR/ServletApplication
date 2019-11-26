package com.leverx.servletapp.cat.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class CatDto {

    private List<Integer> idList;

    @NotNull
    @Size(min = 5, max = 60)
    private String name;

    @NotNull
    @PastOrPresent
    private Date dateOfBirth;
}

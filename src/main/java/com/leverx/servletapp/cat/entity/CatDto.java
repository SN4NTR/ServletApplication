package com.leverx.servletapp.cat.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class CatDto {

    @NotNull
    @Size(min = 5, max = 60)
    private String name;

    @PastOrPresent
    private Date dateOfBirth;
}

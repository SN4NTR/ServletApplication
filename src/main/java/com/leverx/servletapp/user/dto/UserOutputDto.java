package com.leverx.servletapp.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserOutputDto {

    @NonNull
    private int id;

    @NonNull
    @NotNull
    @Size(min = 5, max = 60)
    private String firstName;
}

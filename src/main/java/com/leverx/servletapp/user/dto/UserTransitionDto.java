package com.leverx.servletapp.user.dto;

import lombok.Getter;

import java.util.List;

import static java.util.Collections.emptyList;

@Getter
public class UserTransitionDto {

    private int idFrom;
    private int idTo;
    private List<Integer> catIds = emptyList();
}

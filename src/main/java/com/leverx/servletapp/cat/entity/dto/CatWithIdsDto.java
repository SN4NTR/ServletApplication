package com.leverx.servletapp.cat.entity.dto;

import lombok.Getter;

import java.util.List;

import static java.util.Collections.emptyList;

@Getter
public class CatWithIdsDto {

    private List<Integer> ids = emptyList();
}

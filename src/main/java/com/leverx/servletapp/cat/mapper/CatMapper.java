package com.leverx.servletapp.cat.mapper;

import com.google.gson.Gson;
import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;

import java.io.BufferedReader;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

public class CatMapper {

    private final static Gson GSON = new Gson();

    public static String readJsonBody(BufferedReader reader) {
        return reader.lines()
                .collect(joining());
    }

    public static CatDto jsonToCatDto(String jsonBody) {
        return GSON.fromJson(jsonBody, CatDto.class);
    }

    public static String catToJson(Cat cat) {
        return GSON.toJson(cat);
    }

    public static String collectionToJson(Collection<Cat> cats) {
        return cats.stream()
                .map(CatMapper::catToJson)
                .collect(joining("\n"));
    }

    public static Cat catDtoToCat(CatDto catDto) {
        var name = catDto.getName();
        Cat cat = new Cat();
        cat.setName(name);
        return cat;
    }
}

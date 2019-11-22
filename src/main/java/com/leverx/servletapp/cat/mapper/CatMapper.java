package com.leverx.servletapp.cat.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.servletapp.cat.entity.Cat;
import com.leverx.servletapp.cat.entity.CatDto;

import javax.ws.rs.InternalServerErrorException;
import java.io.BufferedReader;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

public class CatMapper {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String readJsonBody(BufferedReader reader) {
        return reader.lines()
                .collect(joining());
    }

    public static CatDto jsonToCatDto(String jsonBody) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(jsonBody, CatDto.class);
    }

    private static String catToJson(Cat cat) {
        try {
            return OBJECT_MAPPER.writeValueAsString(cat);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(e);
        }
    }

    public static String collectionToJson(Collection<Cat> cats) {
        return cats.stream()
                .map(CatMapper::catToJson)
                .collect(joining("\n"));
    }

    public static Cat catDtoToCat(CatDto catDto) {
        var cat = new Cat();
        var name = catDto.getName();
        cat.setName(name);
        var dateOfBirth = catDto.getDateOfBirth();
        cat.setDateOfBirth(dateOfBirth);
        return cat;
    }
}

package com.leverx.servletapp.user.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.leverx.servletapp.cat.entity.CatDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserDto {

    private int id;

    @NotNull
    @Size(min = 5, max = 60)
    private String firstName;

    @JsonInclude(NON_EMPTY)
    private List<CatDto> cats;

    @JsonProperty(access = WRITE_ONLY)
    private List<Integer> catsIdList;
}

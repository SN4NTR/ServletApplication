package com.leverx.servletapp.cat.entity;

import com.leverx.servletapp.animal.entity.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.leverx.servletapp.cat.validator.CatValidator.MIN_VALUE;

@Table
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Cat extends Animal {

    @NotNull
    @Min(value = MIN_VALUE)
    @Column(nullable = false)
    private int miceCaught;

    public Cat(String name, LocalDate dateOfBirth, int miceCaught) {
        super(name, dateOfBirth);
        this.miceCaught = miceCaught;
    }
}

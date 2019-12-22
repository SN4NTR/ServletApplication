package com.leverx.servletapp.model.animal.dog.entity;

import com.leverx.servletapp.model.animal.parent.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.MIN_VALUE;

@Table
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Dog extends Animal {

    @NotNull
    @Min(value = MIN_VALUE)
    @Column(nullable = false)
    private int goodBoyAmount;

    public Dog(String name, LocalDate dateOfBirth, int goodBoyAmount) {
        super(name, dateOfBirth);
        this.goodBoyAmount = goodBoyAmount;
    }
}

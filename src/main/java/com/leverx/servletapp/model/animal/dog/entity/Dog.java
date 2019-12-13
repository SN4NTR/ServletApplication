package com.leverx.servletapp.model.animal.dog.entity;

import com.leverx.servletapp.model.animal.dog.validator.DogValidator;
import com.leverx.servletapp.model.animal.parent.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.time.LocalDate;

import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.MIN_VALUE;
import static com.leverx.servletapp.model.animal.dog.validator.DogValidator.WRONG_VALUE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dogs")
public class Dog extends Animal {

    @Min(value = MIN_VALUE, message = WRONG_VALUE)
    @Column(name = "good_boy_amount")
    private int goodBoyAmount;

    public Dog(String name, LocalDate dateOfBirth, int goodBoyAmount) {
        super(name, dateOfBirth);
        this.goodBoyAmount = goodBoyAmount;
    }
}

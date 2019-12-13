package com.leverx.servletapp.model.animal.cat.entity;

import com.leverx.servletapp.model.animal.cat.validator.CatValidator;
import com.leverx.servletapp.model.animal.parent.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.time.LocalDate;

import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.MIN_VALUE;
import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.WRONG_VALUE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cats")
public class Cat extends Animal {

    @Min(value = MIN_VALUE, message = WRONG_VALUE)
    @Column(name = "mice_caught")
    private int miceCaught;

    public Cat(String name, LocalDate dateOfBirth, int miceCaught) {
        super(name, dateOfBirth);
        this.miceCaught = miceCaught;
    }
}

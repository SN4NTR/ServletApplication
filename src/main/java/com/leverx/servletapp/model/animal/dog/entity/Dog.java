package com.leverx.servletapp.model.animal.dog.entity;

import com.leverx.servletapp.model.animal.parent.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dogs")
public class Dog extends Animal {

    public Dog(String name, LocalDate dateOfBirth) {
        setName(name);
        setDateOfBirth(dateOfBirth);
    }
}

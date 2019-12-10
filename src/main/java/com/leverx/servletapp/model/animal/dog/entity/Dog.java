package com.leverx.servletapp.model.animal.dog.entity;

import com.leverx.servletapp.model.animal.Animal;
import com.leverx.servletapp.model.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.WRONG_DATE_MSG;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dogs")
@RequiredArgsConstructor
public class Dog extends Animal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Column(name = "name")
    private String name;

    @NonNull
    @NotNull
    @Column(name = "date_of_birth")
    @PastOrPresent(message = WRONG_DATE_MSG)
    private LocalDate dateOfBirth;
}

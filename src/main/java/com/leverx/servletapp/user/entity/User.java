package com.leverx.servletapp.user.entity;

import com.leverx.servletapp.animal.entity.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

import static com.leverx.servletapp.user.validator.UserValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.user.validator.UserValidator.NAME_MIN_SIZE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Table
@Getter
@Setter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NonNull
    @Column(nullable = false, length = NAME_MAX_SIZE)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    private String firstName;

    @Email
    @NonNull
    @Column(nullable = false)
    private String email;

    @NonNull
    @Column(columnDefinition = "integer default 0")
    private int animalPoints;

    @NonNull
    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "owner_animal",
            joinColumns = {@JoinColumn(name = "owner_id")},
            inverseJoinColumns = {@JoinColumn(name = "animal_id")})
    private List<Animal> animals;
}

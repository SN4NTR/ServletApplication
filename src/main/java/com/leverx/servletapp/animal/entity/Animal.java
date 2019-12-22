package com.leverx.servletapp.animal.entity;

import com.leverx.servletapp.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.leverx.servletapp.animal.validator.AnimalValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.animal.validator.AnimalValidator.NAME_MIN_SIZE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@RequiredArgsConstructor
@Inheritance(strategy = JOINED)
public abstract class Animal {

    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NonNull
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    @Column(nullable = false, length = NAME_MAX_SIZE)
    private String name;

    @NonNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "owner_animal",
            joinColumns = {@JoinColumn(name = "animal_id")},
            inverseJoinColumns = {@JoinColumn(name = "owner_id")})
    private List<User> owners;
}

package com.leverx.servletapp.model.animal;

import com.leverx.servletapp.model.user.entity.User;
import lombok.Getter;
import lombok.NonNull;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.NAME_MIN_SIZE;
import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.WRONG_DATE_MSG;
import static com.leverx.servletapp.model.animal.cat.validator.CatValidator.WRONG_NAME_SIZE_MSG;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;

@Getter
@Setter
@Entity
@Table(name = "animals")
@Inheritance(strategy = JOINED)
public abstract class Animal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Column(name = "name")
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = WRONG_NAME_SIZE_MSG)
    private String name;

    @NonNull
    @NotNull
    @Column(name = "date_of_birth")
    @PastOrPresent(message = WRONG_DATE_MSG)
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "owner_animal",
            joinColumns = {@JoinColumn(name = "animal_id")},
            inverseJoinColumns = {@JoinColumn(name = "owner_id")})
    private List<User> owners;
}

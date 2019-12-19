package com.leverx.servletapp.model.animal.parent;

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
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.model.animal.parent.validator.AnimalValidator.NAME_MIN_SIZE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
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
    @NotNull
    @Column
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    private String name;

    @NonNull
    @NotNull
    @Column
    @PastOrPresent
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = EAGER)
    private List<User> owners;
}

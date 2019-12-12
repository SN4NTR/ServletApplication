package com.leverx.servletapp.model.user.entity;

import com.leverx.servletapp.model.animal.parent.Animal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.leverx.servletapp.model.user.validator.UserValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.model.user.validator.UserValidator.NAME_MIN_SIZE;
import static com.leverx.servletapp.model.user.validator.UserValidator.WRONG_NAME_SIZE_MSG;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Column(name = "first_name")
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = WRONG_NAME_SIZE_MSG)
    private String firstName;

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "owner_animal",
            joinColumns = {@JoinColumn(name = "owner_id")},
            inverseJoinColumns = {@JoinColumn(name = "animal_id")})
    private List<Animal> animals;
}

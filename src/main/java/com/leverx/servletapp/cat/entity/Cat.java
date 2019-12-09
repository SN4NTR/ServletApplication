package com.leverx.servletapp.cat.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static com.leverx.servletapp.cat.validator.CatValidator.NAME_MAX_SIZE;
import static com.leverx.servletapp.cat.validator.CatValidator.NAME_MIN_SIZE;
import static com.leverx.servletapp.cat.validator.CatValidator.WRONG_DATE_MSG;
import static com.leverx.servletapp.cat.validator.CatValidator.WRONG_NAME_SIZE_MSG;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "cats")
@NoArgsConstructor
@RequiredArgsConstructor
public class Cat {

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
    @PastOrPresent(message = WRONG_DATE_MSG)
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;
}

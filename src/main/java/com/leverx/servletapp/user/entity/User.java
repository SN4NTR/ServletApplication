package com.leverx.servletapp.user.entity;

import com.leverx.servletapp.cat.entity.Cat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column
    @NonNull
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column
    @NonNull
    @NotNull
    @Size(min = 1, max = 60)
    private String firstName;

    @OneToMany(fetch = LAZY,
            cascade = REMOVE,
            mappedBy = "owner")
    private List<Cat> cats;
}

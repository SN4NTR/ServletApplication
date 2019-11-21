package com.leverx.servletapp.user.entity;

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
    @NonNull
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(name = "first_name")
    @NonNull
    private String firstName;

//    @OneToMany(fetch = EAGER,
//            cascade = REMOVE,
//            mappedBy = "owner")
//    private List<Cat> cats;
}

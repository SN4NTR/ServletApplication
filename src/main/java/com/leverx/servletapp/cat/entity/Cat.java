package com.leverx.servletapp.cat.entity;

import com.leverx.servletapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "cats")
@AllArgsConstructor
@NoArgsConstructor
public class Cat {

    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column
    @NotNull
    @Size(min = 1, max = 60)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
}

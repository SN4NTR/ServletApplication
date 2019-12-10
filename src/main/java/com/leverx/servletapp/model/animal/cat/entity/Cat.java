package com.leverx.servletapp.model.animal.cat.entity;

import com.leverx.servletapp.model.animal.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cats")
public class Cat extends Animal {
}

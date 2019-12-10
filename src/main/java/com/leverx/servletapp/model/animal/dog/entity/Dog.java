package com.leverx.servletapp.model.animal.dog.entity;

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
@Table(name = "dogs")
public class Dog extends Animal {
}

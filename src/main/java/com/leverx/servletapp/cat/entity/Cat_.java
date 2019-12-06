package com.leverx.servletapp.cat.entity;

import com.leverx.servletapp.user.entity.User;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cat.class)
public abstract class Cat_ {

    public static volatile SingularAttribute<Cat, Integer> id;
    public static volatile SingularAttribute<Cat, User> owner;
}

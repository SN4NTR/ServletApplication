package com.leverx.servletapp.factory;

import com.leverx.servletapp.animal.repository.AnimalRepositoryImpl;
import com.leverx.servletapp.animal.service.AnimalServiceImpl;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.cat.service.CatServiceImpl;
import com.leverx.servletapp.dog.repository.DogRepositoryImpl;
import com.leverx.servletapp.dog.service.DogServiceImpl;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import com.leverx.servletapp.user.service.UserServiceImpl;

public class BeanFactory {

    public static UserServiceImpl getUserService() {
        return new UserServiceImpl(new UserRepositoryImpl(), new CatRepositoryImpl(), new DogRepositoryImpl());
    }

    public static AnimalServiceImpl getAnimalService() {
        return new AnimalServiceImpl(new AnimalRepositoryImpl());
    }

    public static CatServiceImpl getCatService() {
        return new CatServiceImpl(new CatRepositoryImpl());
    }

    public static DogServiceImpl getDogService() {
        return new DogServiceImpl(new DogRepositoryImpl());
    }
}

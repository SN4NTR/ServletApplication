package com.leverx.servletapp.factory;

import com.leverx.servletapp.animal.repository.AnimalRepositoryImpl;
import com.leverx.servletapp.animal.service.AnimalService;
import com.leverx.servletapp.animal.service.AnimalServiceImpl;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.cat.service.CatService;
import com.leverx.servletapp.cat.service.CatServiceImpl;
import com.leverx.servletapp.dog.repository.DogRepositoryImpl;
import com.leverx.servletapp.dog.service.DogService;
import com.leverx.servletapp.dog.service.DogServiceImpl;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;

public class BeanFactory {

    public static UserService getUserService() {
        return new UserServiceImpl(new UserRepositoryImpl());
    }

    public static AnimalService getAnimalService() {
        return new AnimalServiceImpl(new AnimalRepositoryImpl());
    }

    public static CatService getCatService() {
        return new CatServiceImpl(new CatRepositoryImpl());
    }

    public static DogService getDogService() {
        return new DogServiceImpl(new DogRepositoryImpl());
    }
}

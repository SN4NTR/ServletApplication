package com.leverx.servletapp.core.factory;

import com.leverx.servletapp.animal.repository.AnimalRepositoryImpl;
import com.leverx.servletapp.animal.service.AnimalService;
import com.leverx.servletapp.animal.service.AnimalServiceImpl;
import com.leverx.servletapp.animal.validator.AnimalValidator;
import com.leverx.servletapp.cat.repository.CatRepositoryImpl;
import com.leverx.servletapp.cat.service.CatService;
import com.leverx.servletapp.cat.service.CatServiceImpl;
import com.leverx.servletapp.cat.validator.CatValidator;
import com.leverx.servletapp.dog.repository.DogRepositoryImpl;
import com.leverx.servletapp.dog.service.DogService;
import com.leverx.servletapp.dog.service.DogServiceImpl;
import com.leverx.servletapp.dog.validator.DogValidator;
import com.leverx.servletapp.user.repository.UserRepositoryImpl;
import com.leverx.servletapp.user.service.UserService;
import com.leverx.servletapp.user.service.UserServiceImpl;
import com.leverx.servletapp.user.validator.UserValidator;

public class BeanFactory {

    public static UserService getUserService() {
        var userRepository = new UserRepositoryImpl();
        var userValidator = getUserValidator();
        return new UserServiceImpl(userRepository, userValidator);
    }

    public static AnimalService getAnimalService() {
        var animalRepository = new AnimalRepositoryImpl();
        var userValidator = getUserValidator();
        var animalValidator = getAnimalValidator();
        return new AnimalServiceImpl(animalRepository, animalValidator, userValidator);
    }

    public static CatService getCatService() {
        var catRepository = new CatRepositoryImpl();
        var catValidator = getCatValidator();
        var userValidator = getUserValidator();
        return new CatServiceImpl(catRepository, catValidator, userValidator);
    }

    public static DogService getDogService() {
        var dogRepository = new DogRepositoryImpl();
        var dogValidator = getDogValidator();
        var userValidator = getUserValidator();
        return new DogServiceImpl(dogRepository, dogValidator, userValidator);
    }

    private static UserValidator getUserValidator() {
        var userRepository = new UserRepositoryImpl();
        var catValidator = getCatValidator();
        var dogValidator = getDogValidator();
        return new UserValidator(userRepository, catValidator, dogValidator);
    }

    private static CatValidator getCatValidator() {
        var catRepository = new CatRepositoryImpl();
        return new CatValidator(catRepository);
    }

    private static DogValidator getDogValidator() {
        var dogRepository = new DogRepositoryImpl();
        return new DogValidator(dogRepository);
    }

    private static AnimalValidator getAnimalValidator() {
        var animalRepository = new AnimalRepositoryImpl();
        return new AnimalValidator(animalRepository);
    }
}

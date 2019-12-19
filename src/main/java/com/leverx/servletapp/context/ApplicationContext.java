package com.leverx.servletapp.context;

import com.leverx.servletapp.annotation.Repository;
import com.leverx.servletapp.annotation.Service;
import com.leverx.servletapp.exception.InternalServerErrorException;
import com.leverx.servletapp.model.user.servlet.UserServlet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import javax.servlet.Servlet;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ApplicationContext {

    private static Map<Class<?>, Class<?>> componentsMap = new HashMap<>();

    private static final String PACKAGE_NAME = "com.leverx.servletapp.model";

    public static void loadApplicationContext() {
        loadComponents(Service.class);
        loadComponents(Repository.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> interfaceClass) {
        var implementationClass = componentsMap.get(interfaceClass);
        try {
            return (T) implementationClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getMessage());
            throw new InternalServerErrorException();
        }
    }

    private static <T extends Annotation> void loadComponents(Class<T> tClass) {
        var reflections = new Reflections(PACKAGE_NAME);
        var components = reflections.getTypesAnnotatedWith(tClass);

        for (var implementationClass : components) {
            for (var interfaceName : implementationClass.getInterfaces()) {
                componentsMap.put(interfaceName, implementationClass);
            }
        }
    }
}

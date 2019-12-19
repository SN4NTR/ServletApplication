package com.leverx.servletapp.context;

import com.leverx.servletapp.annotation.Repository;
import com.leverx.servletapp.annotation.Service;
import com.leverx.servletapp.exception.InternalServerErrorException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ApplicationContext {

    private static Map<Class<?>, Class<?>> componentsMap = new HashMap<>();

    private static ApplicationContext applicationContext;

    private static final String PACKAGE_NAME = "com.leverx.servletapp.model";

    static {
        loadComponents(Service.class);
        loadComponents(Repository.class);
    }

    public static synchronized ApplicationContext getInstance() {
        if (isNull(applicationContext)) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }

    public static Object getBean(Class<?> interfaceClass) {
        var implementationClass = componentsMap.get(interfaceClass);
        try {
            return implementationClass.getDeclaredConstructor().newInstance();
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

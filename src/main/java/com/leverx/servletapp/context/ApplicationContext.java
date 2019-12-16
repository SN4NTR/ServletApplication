package com.leverx.servletapp.context;

import com.leverx.servletapp.annotation.Repository;
import com.leverx.servletapp.annotation.Service;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

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

    private static final String PACKAGE_NAME = "com.leverx.servletapp";

    static {
        loadServices();
        loadRepositories();
    }

    public static synchronized ApplicationContext getInstance() {
        if (isNull(applicationContext)) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }

    // TODO return Optional
    public static Object getBean(Class<?> interfaceClass) {
        var implementationClass = componentsMap.get(interfaceClass);
        try {
            return implementationClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private static void loadServices() {
        var reflections = new Reflections(PACKAGE_NAME);
        var services = reflections.getTypesAnnotatedWith(Service.class);

        for (var implementationClass : services) {
            for (var interfaceName : implementationClass.getInterfaces()) {
                componentsMap.put(interfaceName, implementationClass);
            }
        }
    }

    private static void loadRepositories() {
        var reflections = new Reflections(PACKAGE_NAME);
        var repositories = reflections.getTypesAnnotatedWith(Repository.class);

        for (var implementationClass : repositories) {
            for (var interfaceName : implementationClass.getInterfaces()) {
                componentsMap.put(interfaceName, implementationClass);
            }
        }
    }
}

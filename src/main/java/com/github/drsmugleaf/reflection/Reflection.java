package com.github.drsmugleaf.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 21/05/2017.
 */
public class Reflection {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reflection.class);

    private final String PACKAGE_NAME;

    public Reflection(String packageName) {
        PACKAGE_NAME = packageName;
    }

    public List<Class<?>> getClasses() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = PACKAGE_NAME.replace('.', '/');
        Enumeration<URL> resources;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            throw new ReflectionException("Error getting resources in class loader from path " + path, e);
        }

        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            URI uri;
            try {
                uri = new URI(resource.toString());
            } catch (URISyntaxException e) {
                throw new ReflectionException("Error creating URI object for resource " + resource, e);
            }

            dirs.add(new File(uri.getPath()));
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, PACKAGE_NAME));
        }

        return classes;
    }

    private List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if(files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                Class<?> clazz;
                String className = null;
                try {
                    String fileName = file.getName();
                    className = fileName.substring(0, fileName.length() - 6);
                    clazz = Class.forName(packageName + '.' + className);
                } catch (ClassNotFoundException e) {
                    throw new ReflectionException("Class " + className + " in package " + packageName + " not found", e);
                } catch (Exception e) {
                    return classes;
                }

                classes.add(clazz);
            }
        }
        return classes;
    }

    public List<Method> findMethodsWithAnnotation(Class<? extends Annotation> annotation) {
        Iterable<Class<?>> classes = getClasses();
        List<Method> methodList = new ArrayList<>();

        classes.forEach(cls -> {
            for (Method method : cls.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    methodList.add(method);
                }
            }
        });

        return methodList;
    }

    public List<Class<?>> findClassesWithAnnotation(Class<? extends Annotation> annotation) {
        List<Class<?>> classes = getClasses();
        classes.removeIf(cls -> !cls.isAnnotationPresent(annotation));
        return classes;
    }

    public List<Class<?>> findClassesWithMethodAnnotation(Class<? extends Annotation> annotation) {
        List<Class<?>> classes = getClasses();
        return classes.stream().filter(clazz -> {
            return Arrays.stream(clazz.getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(annotation));
        }).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<Class<T>> findSubtypesOf(Class<T> supertype) {
        List<Class<T>> classes = new ArrayList<>();
        for (Class<?> clazz : getClasses()) {
            if (supertype.isAssignableFrom(clazz)) {
                classes.add((Class<T>) clazz);
            }
        }

        classes.remove(supertype);

        return classes;
    }

}

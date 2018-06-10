package com.github.drsmugleaf.util;

import com.github.drsmugleaf.BanterBot4J;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by DrSmugleaf on 21/05/2017.
 */
public class Reflection {

    @Nonnull
    private final String PACKAGE_NAME;

    public Reflection(@Nonnull String packageName) {
        PACKAGE_NAME = packageName;
    }

    @Nonnull
    public List<Class<?>> getClasses() throws ClassNotFoundException, IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = PACKAGE_NAME.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            URI uri = new URI(resource.toString());
            dirs.add(new File(uri.getPath()));
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, PACKAGE_NAME));
        }

        return classes;
    }

    @Nonnull
    private List<Class<?>> findClasses(@Nonnull File directory, @Nonnull String packageName) throws ClassNotFoundException {
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
            }
            else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    @Nonnull
    public List<Method> findMethodsWithAnnotation(@Nonnull Class<? extends Annotation> annotation) {
        Iterable<Class<?>> classes = null;
        try {
            classes = getClasses();
        } catch(ClassNotFoundException | IOException | URISyntaxException e) {
            BanterBot4J.LOGGER.error("Error finding methods with annotation " + annotation.getName(), e);
        }

        List<Method> methodList = new ArrayList<>();
        if(classes == null) {
            return methodList;
        }

        classes.forEach(cls -> {
            for (Method method : cls.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    methodList.add(method);
                }
            }
        });

        return methodList;
    }

    @Nonnull
    public List<Class<?>> findClassesWithAnnotation(@Nonnull Class<? extends Annotation> annotation) {
        List<Class<?>> classes = new ArrayList<>();

        try {
            classes.addAll(getClasses());
        } catch (IOException | URISyntaxException | ClassNotFoundException e) {
            BanterBot4J.LOGGER.error("Error finding classes with annotation" + annotation.getName(), e);
        }

        classes.removeIf(cls -> !cls.isAnnotationPresent(annotation));

        return classes;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> List<Class<T>> findSubtypesOf(@Nonnull Class<T> supertype) {
        List<Class<T>> classes = new ArrayList<>();

        try {
            for (Class<?> clazz : getClasses()) {
                if (supertype.isAssignableFrom(clazz)) {
                    classes.add((Class<T>) clazz);
                }
            }
        } catch (ClassNotFoundException | IOException | URISyntaxException e) {
            BanterBot4J.LOGGER.error("Error finding subtypes of class" + supertype.getName(), e);
        }

        return classes;
    }

}

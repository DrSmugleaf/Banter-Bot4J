package com.github.drsmugleaf.blackjack;

import com.github.drsmugleaf.BanterBot4J;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class EventDispatcher {

    @Nonnull
    private static final Set<Method> LISTENERS = new HashSet<>();

    public static void registerListeners(@Nonnull Class<?> clazz) {
        Set<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(BlackjackEventHandler.class))
                .collect(Collectors.toSet());

        LISTENERS.addAll(methods);
    }

    public static void registerListeners(@Nonnull Class<?>... classes) {
        for (Class<?> clazz : classes) {
            registerListeners(clazz);
        }
    }

    public static void registerListeners(@Nonnull List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            registerListeners(clazz);
        }
    }

    static void dispatch(@Nonnull Event event) {
        for (Method listener : LISTENERS) {
            if (listener.getAnnotation(BlackjackEventHandler.class).event() == event.getClass()) {
                try {
                    listener.invoke(listener.getDeclaringClass(), event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    BanterBot4J.LOGGER.error("Error invoking event handler " + listener.getName() + " for event " + event.getClass().getName(), e);
                }
            }
        }
    }

}

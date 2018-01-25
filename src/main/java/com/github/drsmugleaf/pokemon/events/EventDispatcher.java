package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.BanterBot4J;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public class EventDispatcher {

    private static final Set<Method> LISTENERS = new HashSet<>();

    public static void registerListener(@Nonnull Object listener) {
        Stream<Method> methods = Arrays.stream(listener.getClass().getMethods()).filter(method -> method.isAnnotationPresent(PokemonEventHandler.class));
        EventDispatcher.LISTENERS.addAll(methods.collect(Collectors.toSet()));
    }

    public static void dispatch(@Nonnull Event event) {
        for (Method listener : EventDispatcher.LISTENERS) {
            if (listener.getAnnotation(PokemonEventHandler.class).event() == event.getClass()) {
                try {
                    listener.invoke(listener.getDeclaringClass(), event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    BanterBot4J.LOGGER.error("Error invoking event handler " + listener.getName() + " for event " + event.getClass().getName(), e);
                }
            }
        }
    }

}

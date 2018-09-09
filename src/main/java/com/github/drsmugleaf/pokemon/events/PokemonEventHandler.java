package com.github.drsmugleaf.pokemon.events;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PokemonEventHandler {

    @Nonnull
    Class<? extends Event> event() default Event.class;

}

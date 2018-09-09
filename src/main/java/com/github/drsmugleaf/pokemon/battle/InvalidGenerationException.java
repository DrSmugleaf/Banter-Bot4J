package com.github.drsmugleaf.pokemon.battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 28/06/2017.
 */
public class InvalidGenerationException extends IllegalArgumentException {

    public InvalidGenerationException() {
        super();
    }

    public InvalidGenerationException(String s) {
        super(s);
    }

    public InvalidGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGenerationException(Throwable cause) {
        super(cause);
    }

    public InvalidGenerationException(@Nonnull Generation generation, @Nonnull String message) {
        super("Invalid generation: " + generation.NAME + ". " + message);
    }

    public InvalidGenerationException(@Nonnull Generation generation) {
        this(generation, "");
    }

}

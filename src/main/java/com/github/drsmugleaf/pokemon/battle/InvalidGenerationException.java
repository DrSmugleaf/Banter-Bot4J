package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.NotNull;

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

    public InvalidGenerationException(@NotNull Generation generation, @NotNull String message) {
        super("Invalid generation: " + generation.NAME + ". " + message);
    }

    public InvalidGenerationException(@NotNull Generation generation) {
        this(generation, "");
    }

}

package com.github.drsmugleaf.pokemon.battle;

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

    public InvalidGenerationException(Generation generation, String message) {
        super("Invalid generation: " + generation.NAME + ". " + message);
    }

    public InvalidGenerationException(Generation generation) {
        this(generation, "");
    }

}

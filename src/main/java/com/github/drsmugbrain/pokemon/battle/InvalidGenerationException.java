package com.github.drsmugbrain.pokemon.battle;

/**
 * Created by DrSmugleaf on 28/06/2017.
 */
public class InvalidGenerationException extends IllegalArgumentException {

    public InvalidGenerationException() {
        super();
    }

    public InvalidGenerationException(String message) {
        super(message);
    }

    public InvalidGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGenerationException(Throwable cause) {
        super(cause);
    }

    public InvalidGenerationException(Generation generation) {
        super("Invalid generation: " + generation.getName());
    }

}

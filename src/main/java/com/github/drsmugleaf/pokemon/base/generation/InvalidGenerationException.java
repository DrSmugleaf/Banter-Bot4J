package com.github.drsmugleaf.pokemon.base.generation;

/**
 * Created by DrSmugleaf on 28/06/2017.
 */
public class InvalidGenerationException extends IllegalArgumentException {

    public InvalidGenerationException(IGeneration generation, String message) {
        super("Invalid generation: " + generation.getName() + ". " + message);
    }

    public InvalidGenerationException(IGeneration generation) {
        this(generation, "");
    }

}

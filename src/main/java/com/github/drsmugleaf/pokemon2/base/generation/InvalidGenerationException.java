package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon.battle.Generation;

/**
 * Created by DrSmugleaf on 28/06/2017.
 */
public class InvalidGenerationException extends IllegalArgumentException {

    public InvalidGenerationException(Generation generation, String message) {
        super("Invalid generation: " + generation.NAME + ". " + message);
    }

    public InvalidGenerationException(Generation generation) {
        this(generation, "");
    }

}

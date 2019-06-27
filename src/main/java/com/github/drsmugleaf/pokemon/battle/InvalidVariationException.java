package com.github.drsmugleaf.pokemon.battle;

/**
 * Created by DrSmugleaf on 26/09/2017.
 */
public class InvalidVariationException extends IllegalArgumentException {

    public InvalidVariationException(Variation variation, String message) {
        super("Invalid variation: " + variation.getName() + ". " + message);
    }

    public InvalidVariationException(Variation variation) {
        this(variation, "");
    }

}

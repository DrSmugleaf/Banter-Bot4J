package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 26/09/2017.
 */
public class InvalidVariationException extends IllegalArgumentException {

    public InvalidVariationException(@Nonnull Variation variation) {
        super("Invalid variation: " + variation.getName());
    }

}

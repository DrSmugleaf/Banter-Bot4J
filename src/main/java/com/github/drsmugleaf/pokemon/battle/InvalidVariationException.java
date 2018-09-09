package com.github.drsmugleaf.pokemon.battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 26/09/2017.
 */
public class InvalidVariationException extends IllegalArgumentException {

    public InvalidVariationException() {
        super();
    }

    public InvalidVariationException(String s) {
        super(s);
    }

    public InvalidVariationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVariationException(Throwable cause) {
        super(cause);
    }

    public InvalidVariationException(@Nonnull Variation variation, @Nonnull String message) {
        super("Invalid variation: " + variation.getName() + ". " + message);
    }

    public InvalidVariationException(@Nonnull Variation variation) {
        this(variation, "");
    }

}

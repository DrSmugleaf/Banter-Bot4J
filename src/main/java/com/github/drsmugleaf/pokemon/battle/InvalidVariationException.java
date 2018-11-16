package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.NotNull;

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

    public InvalidVariationException(@NotNull Variation variation, @NotNull String message) {
        super("Invalid variation: " + variation.getName() + ". " + message);
    }

    public InvalidVariationException(@NotNull Variation variation) {
        this(variation, "");
    }

}

package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public enum Weather {

    SUNSHINE("Sunshine"),
    HARSH_SUNSHINE("Harsh Sunshine"),
    RAIN("Rain"),
    HEAVY_RAIN("Heavy_Rain"),
    HAIL("Hail"),
    SANDSTORM("Sandstorm"),
    STRONG_WINDS("Strong Winds"),
    FOG("Fog"),
    SHADOW_SKY("Shadow Sky");

    private final String NAME;

    Weather(@Nonnull String name) {
        this.NAME = name;
    }

}

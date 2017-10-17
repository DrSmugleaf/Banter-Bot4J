package com.github.drsmugbrain.pokemon.moves;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/10/2017.
 */
public enum EntryHazard {

    SPIKES("Spikes"),
    STEALTH_ROCK("Stealth Rock"),
    STICKY_WEB("Sticky Web"),
    TOXIC_SPIKES("Toxic Spikes");

    @Nonnull
    private final String NAME;

    EntryHazard(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}

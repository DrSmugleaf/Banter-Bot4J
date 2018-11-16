package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 04/10/2017.
 */
public enum EntryHazard {

    SPIKES("Spikes"),
    STEALTH_ROCK("Stealth Rock"),
    STICKY_WEB("Sticky Web"),
    TOXIC_SPIKES("Toxic Spikes");

    @NotNull
    public final String NAME;

    EntryHazard(@NotNull String name) {
        NAME = name;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

}

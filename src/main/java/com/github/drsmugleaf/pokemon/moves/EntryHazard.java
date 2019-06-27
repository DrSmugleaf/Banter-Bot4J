package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 04/10/2017.
 */
public enum EntryHazard {

    SPIKES("Spikes"),
    STEALTH_ROCK("Stealth Rock"),
    STICKY_WEB("Sticky Web"),
    TOXIC_SPIKES("Toxic Spikes");

    public final String NAME;

    EntryHazard(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

}

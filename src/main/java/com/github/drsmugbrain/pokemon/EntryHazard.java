package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/10/2017.
 */
public enum EntryHazard {

    SPIKES("Spikes"),
    STEALTH_ROCK("Stealth Rock"),
    STICKY_WEB("Sticky Web"),
    TOXIC_SPIKES("Toxic Spikes");

    private final String NAME;

    EntryHazard(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return NAME;
    }

}

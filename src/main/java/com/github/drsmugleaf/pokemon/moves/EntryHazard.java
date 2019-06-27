package com.github.drsmugleaf.pokemon.moves;

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

    public String getName() {
        return NAME;
    }

}

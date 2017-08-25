package com.github.drsmugbrain.mafia.settings;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public enum NightTimeActions {

    NIGHT_SEQUENCE("Night Sequence"),
    DEATH_DESCRIPTIONS("Death Descriptions"),
    CLASSIC_NIGHT("Classic Night");

    private final String NAME;

    NightTimeActions(@Nonnull String name) {
        this.NAME = name;
    }

}

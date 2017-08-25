package com.github.drsmugbrain.mafia.settings;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public enum GameStart {

    DAY("Day"),
    DAY_NO_LYNCH("Day/No Lynch"),
    NIGHT("Night");

    private final String NAME;

    GameStart(@Nonnull String name) {
        this.NAME = name;
    }

}

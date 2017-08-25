package com.github.drsmugbrain.mafia.settings;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public enum DayType {

    MAJORITY("Majority"),
    TRIAL("Trial"),
    BALLOT("Ballot"),
    BALLOT_AND_TRIAL("Ballot+Trial");

    private final String NAME;

    DayType(@Nonnull String name) {
        this.NAME = name;
    }

}

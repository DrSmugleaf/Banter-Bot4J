package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public enum Phase {

    DAY("Day"),
    TRIAL("Trial"),
    VOTE("Vote"),
    VOTE_RECOUNT("Vote Recount"),
    LAST_WORDS("Last Words"),
    EXECUTION("Execution"),
    NIGHT("Night"),
    COURT("Court");

    private final String NAME;

    Phase(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

}

package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public enum Roles {

    CITIZEN("Citizen", Teams.TOWN);

    private final String NAME;
    private final Teams TEAM;

    Roles(@Nonnull String name, @Nonnull Teams team) {
        this.NAME = name;
        this.TEAM = team;
    }

    public String getName() {
        return this.NAME;
    }

}

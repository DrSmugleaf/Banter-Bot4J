package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 30/08/2017.
 */
public enum RoleStatuses {

    BODYGUARDED("Bodyguarded"),
    BULLETPROOF_VEST("Bulletproof Vest");

    private final String NAME;

    RoleStatuses(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

}

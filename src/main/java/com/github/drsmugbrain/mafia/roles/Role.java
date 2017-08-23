package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public class Role {

    private final Roles BASE_ROLE;

    Role(@Nonnull Roles role) {
        this.BASE_ROLE = role;
    }

    public Roles getBaseRole() {
        return this.BASE_ROLE;
    }

}

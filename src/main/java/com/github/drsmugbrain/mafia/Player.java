package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 24/08/2017.
 */
public class Player {

    private final Integer ID;
    private final String NAME;
    private Roles ROLE;

    Player(@Nonnull Integer id, @Nonnull String name, @Nonnull Roles role) {
        this.ID = id;
        this.NAME = name;
        this.ROLE = role;
    }

    public Integer getID() {
        return this.ID;
    }

    public String getName() {
        return this.NAME;
    }

    public Roles getRole() {
        return this.ROLE;
    }

}

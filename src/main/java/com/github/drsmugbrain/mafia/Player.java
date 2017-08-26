package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Role;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 24/08/2017.
 */
public class Player {

    private final Integer ID;
    private final String NAME;
    private Role ROLE;

    public Player(@Nonnull Integer id, @Nonnull String name, @Nonnull Role role) {
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

    public Role getRole() {
        return this.ROLE;
    }

    protected void setRole(Role role) {
        this.ROLE = role;
    }

}

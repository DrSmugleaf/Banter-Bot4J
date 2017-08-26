package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Role;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 24/08/2017.
 */
public class Player {

    private final Long ID;
    private final String NAME;
    private Role ROLE = null;

    public Player(@Nonnull Long id, @Nonnull String name) {
        this.ID = id;
        this.NAME = name;
    }

    public Long getID() {
        return this.ID;
    }

    public String getName() {
        return this.NAME;
    }

    @Nullable
    public Role getRole() {
        return this.ROLE;
    }

    protected void setRole(Role role) {
        this.ROLE = role;
    }

}

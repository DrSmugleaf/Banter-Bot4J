package com.github.drsmugbrain.mafia.roles;

import com.github.drsmugbrain.mafia.Ability;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public class Role {

    private final Roles BASE_ROLE;
    private final Ability ABILITY;

    public Role(@Nonnull Roles role) {
        this.BASE_ROLE = role;
        this.ABILITY = new Ability(role.getAbility());
    }

    @Nonnull
    public Roles getBaseRole() {
        return this.BASE_ROLE;
    }

    @Nonnull
    public Ability getAbility() {
        return this.ABILITY;
    }

}

package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Roles;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 24/08/2017.
 */
public class Setup {

    private final Integer ID;
    private final String NAME;
    private final List<Roles> ROLES;

    Setup(@Nonnull Integer id, @Nonnull String name, @Nonnull List<Roles> roles) {
        this.ID = id;
        this.NAME = name;
        this.ROLES = roles;
    }

    public Integer getID() {
        return this.ID;
    }

    public String getName() {
        return this.NAME;
    }

    public List<Roles> getRoles() {
        return this.ROLES;
    }

}

package com.github.drsmugleaf.pokemon.trainer;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 29/09/2017.
 */
public class Player {

    @Nonnull
    public final Long ID;

    @Nonnull
    private String name;

    protected Player(@Nonnull Long id, @Nonnull String name) {
        ID = id;
        this.name = name;
    }

    @Nonnull
    public Long getID() {
        return ID;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

}

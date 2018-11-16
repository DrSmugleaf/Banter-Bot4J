package com.github.drsmugleaf.pokemon.trainer;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 29/09/2017.
 */
public class Player {

    @NotNull
    public final Long ID;

    @NotNull
    private String name;

    protected Player(@NotNull Long id, @NotNull String name) {
        ID = id;
        this.name = name;
    }

    @NotNull
    public Long getID() {
        return ID;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

}

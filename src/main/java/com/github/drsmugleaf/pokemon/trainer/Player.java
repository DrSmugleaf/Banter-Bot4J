package com.github.drsmugleaf.pokemon.trainer;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 29/09/2017.
 */
public class Player {

    public final Long ID;
    private String name;

    protected Player(Long id, String name) {
        ID = id;
        this.name = name;
    }

    @Contract(pure = true)
    public Long getID() {
        return ID;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

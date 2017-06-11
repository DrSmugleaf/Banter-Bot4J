package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Trainer {

    private final String NAME;
    private final List<Pokemon> POKEMONS = new ArrayList<>();

    public Trainer(@Nonnull String name, @Nonnull Pokemon... pokemons) {
        this.NAME = name;
        this.POKEMONS.addAll(Arrays.asList(pokemons));
    }

    public String getName() {
        return this.NAME;
    }

    public Pokemon[] getPokemons() {
        return this.POKEMONS.toArray(new Pokemon[0]);
    }

}

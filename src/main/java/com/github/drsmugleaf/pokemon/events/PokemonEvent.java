package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class PokemonEvent extends Event {

    @NotNull
    public final Pokemon POKEMON;

    public PokemonEvent(@NotNull Pokemon pokemon) {
        super(pokemon.getBattle());
        POKEMON = pokemon;
    }

    @NotNull
    public Pokemon getPokemon() {
        return POKEMON;
    }

}

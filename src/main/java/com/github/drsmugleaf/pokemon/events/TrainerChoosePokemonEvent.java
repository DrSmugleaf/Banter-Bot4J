package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChoosePokemonEvent extends TrainerEvent {

    private final Pokemon POKEMON;

    public TrainerChoosePokemonEvent(@Nonnull Pokemon pokemon) {
        super(pokemon.getTrainer());
        this.POKEMON = pokemon;
    }

    @Nonnull
    public Pokemon getPokemon() {
        return this.POKEMON;
    }

}
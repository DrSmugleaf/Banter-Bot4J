package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerSendOutPokemonEvent extends TrainerEvent {

    private final Pokemon POKEMON;

    public TrainerSendOutPokemonEvent(@Nonnull Pokemon pokemon) {
        super(pokemon.getTrainer());
        this.POKEMON = pokemon;
    }

    @Nonnull
    public Pokemon getPokemon() {
        return this.POKEMON;
    }

}

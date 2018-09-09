package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerSendOutPokemonEvent extends TrainerEvent {

    @Nonnull
    public final Pokemon POKEMON;

    public TrainerSendOutPokemonEvent(@Nonnull Pokemon pokemon) {
        super(pokemon.getTrainer());
        POKEMON = pokemon;
    }

    @Nonnull
    public Pokemon getPokemon() {
        return POKEMON;
    }

}

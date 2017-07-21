package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChoosePokemonEvent extends TrainerEvent {

    private final Pokemon POKEMON;

    public TrainerChoosePokemonEvent(Pokemon pokemon) {
        super(pokemon.getTrainer());
        this.POKEMON = pokemon;
    }

    public Pokemon getPokemon() {
        return this.POKEMON;
    }

}

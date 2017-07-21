package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerSendBackPokemonEvent extends TrainerSendOutPokemonEvent {

    public TrainerSendBackPokemonEvent(Pokemon pokemon) {
        super(pokemon);
    }

}

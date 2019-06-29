package com.github.drsmugleaf.pokemon.events.trainer;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerSendOutPokemonEvent extends TrainerEvent {

    public final Pokemon POKEMON;

    public TrainerSendOutPokemonEvent(Pokemon pokemon) {
        super(pokemon.getTrainer());
        POKEMON = pokemon;
    }

    @Contract(pure = true)
    public Pokemon getPokemon() {
        return POKEMON;
    }

}

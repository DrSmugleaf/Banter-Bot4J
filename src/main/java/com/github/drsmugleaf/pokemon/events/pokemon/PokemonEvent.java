package com.github.drsmugleaf.pokemon.events.pokemon;

import com.github.drsmugleaf.pokemon.events.Event;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class PokemonEvent extends Event {

    public final Pokemon POKEMON;

    public PokemonEvent(Pokemon pokemon) {
        super(pokemon.getBattle());
        POKEMON = pokemon;
    }

    @Contract(pure = true)
    public Pokemon getPokemon() {
        return POKEMON;
    }

}

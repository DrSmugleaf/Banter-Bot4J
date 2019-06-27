package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public class PokemonDamagedEvent extends PokemonEvent {

    public final int DAMAGE;

    public PokemonDamagedEvent(Pokemon defender, int damage) {
        super(defender);
        DAMAGE = damage;
    }

    @Contract(pure = true)
    public int getDamage() {
        return DAMAGE;
    }

}

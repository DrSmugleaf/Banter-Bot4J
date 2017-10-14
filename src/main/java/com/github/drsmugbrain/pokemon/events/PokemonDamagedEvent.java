package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public class PokemonDamagedEvent extends PokemonEvent {

    private final int DAMAGE;

    public PokemonDamagedEvent(@Nonnull Pokemon defender, int damage) {
        super(defender);
        this.DAMAGE = damage;
    }

    public int getDamage() {
        return this.DAMAGE;
    }

}

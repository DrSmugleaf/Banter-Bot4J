package com.github.drsmugbrain.pokemon.status;

import com.github.drsmugbrain.pokemon.moves.Action;
import com.github.drsmugbrain.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 11/10/2017.
 */
public interface IStatus {

    void apply(@Nonnull Pokemon pokemon, @Nonnull Action action);

    void remove(@Nonnull Pokemon pokemon);

}

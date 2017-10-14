package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.moves.Move;
import com.github.drsmugbrain.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChooseTargetEvent extends TrainerChooseMoveEvent {

    private final Pokemon TARGET;

    public TrainerChooseTargetEvent(@Nonnull Pokemon pokemon, @Nonnull Move move, @Nonnull Pokemon target) {
        super(pokemon, move);
        this.TARGET = target;
    }

    @Nonnull
    public Pokemon getTarget() {
        return this.TARGET;
    }

}

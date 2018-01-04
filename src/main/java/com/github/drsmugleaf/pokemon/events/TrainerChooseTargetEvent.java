package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

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
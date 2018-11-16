package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChooseTargetEvent extends TrainerChooseMoveEvent {

    @NotNull
    public final Pokemon TARGET;

    public TrainerChooseTargetEvent(@NotNull Pokemon pokemon, @NotNull Move move, @NotNull Pokemon target) {
        super(pokemon, move);
        TARGET = target;
    }

    @NotNull
    public Pokemon getTarget() {
        return TARGET;
    }

}

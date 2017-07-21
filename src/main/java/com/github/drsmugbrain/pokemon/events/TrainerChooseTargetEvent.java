package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Move;
import com.github.drsmugbrain.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChooseTargetEvent extends TrainerChooseMoveEvent {

    private final Pokemon TARGET;

    public TrainerChooseTargetEvent(Pokemon pokemon, Move move, Pokemon target) {
        super(pokemon, move);
        this.TARGET = target;
    }

    public Pokemon getTarget() {
        return this.TARGET;
    }

}

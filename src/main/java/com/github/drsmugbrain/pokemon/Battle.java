package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Battle {

    private final Trainer TRAINERS[];

    public Battle(@Nonnull Trainer... trainers) {
        this.TRAINERS = trainers;
    }

}

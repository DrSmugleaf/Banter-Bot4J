package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Trainer;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public abstract class TrainerEvent extends Event {

    private final Trainer TRAINER;

    public TrainerEvent(@Nonnull Trainer trainer) {
        super(trainer.getBattle());
        this.TRAINER = trainer;
    }

    @Nonnull
    public Trainer getTrainer() {
        return this.TRAINER;
    }

}

package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.trainer.Trainer;

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

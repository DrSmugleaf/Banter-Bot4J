package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.trainer.Trainer;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public abstract class TrainerEvent extends Event {

    @NotNull
    public final Trainer TRAINER;

    public TrainerEvent(@NotNull Trainer trainer) {
        super(trainer.BATTLE);
        TRAINER = trainer;
    }

    @NotNull
    public Trainer getTrainer() {
        return TRAINER;
    }

}

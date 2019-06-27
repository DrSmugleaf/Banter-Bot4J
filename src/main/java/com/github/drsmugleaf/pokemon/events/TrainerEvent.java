package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.trainer.Trainer;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public abstract class TrainerEvent extends Event {

    public final Trainer TRAINER;

    public TrainerEvent(Trainer trainer) {
        super(trainer.BATTLE);
        TRAINER = trainer;
    }

    public Trainer getTrainer() {
        return TRAINER;
    }

}

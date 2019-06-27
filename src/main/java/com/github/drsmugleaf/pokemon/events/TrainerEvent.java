package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.trainer.Trainer;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public abstract class TrainerEvent extends Event {

    public final Trainer TRAINER;

    public TrainerEvent(Trainer trainer) {
        super(trainer.BATTLE);
        TRAINER = trainer;
    }

    @Contract(pure = true)
    public Trainer getTrainer() {
        return TRAINER;
    }

}

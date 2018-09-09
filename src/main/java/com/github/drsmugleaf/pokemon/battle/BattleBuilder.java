package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.trainer.TrainerBuilder;
import com.github.drsmugleaf.pokemon.trainer.UserException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public class BattleBuilder {

    @Nonnull
    private Setup SETUP = Setup.getDefault();

    @Nonnull
    private final List<TrainerBuilder> TRAINERS = new ArrayList<>();

    public BattleBuilder() {}

    @Nonnull
    public Battle build() throws UserException {
        if (TRAINERS.isEmpty()) {
            throw new IllegalStateException("No trainers have been added to the battle");
        }

        return new Battle(SETUP, TRAINERS);
    }

    @Nonnull
    public Setup getSetup() {
        return SETUP;
    }

    @Nonnull
    public BattleBuilder setSetup(@Nonnull Setup setup) {
        SETUP = setup;
        return this;
    }

    @Nonnull
    public List<TrainerBuilder> getTrainers() {
        return TRAINERS;
    }

    @Nonnull
    public BattleBuilder addTrainer(@Nonnull TrainerBuilder... trainerBuilders) {
        TRAINERS.addAll(Arrays.asList(trainerBuilders));
        return this;
    }

}

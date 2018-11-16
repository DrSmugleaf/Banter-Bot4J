package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.trainer.TrainerBuilder;
import com.github.drsmugleaf.pokemon.trainer.UserException;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public class BattleBuilder {

    @NotNull
    private Setup SETUP = Setup.getDefault();

    @NotNull
    private final List<TrainerBuilder> TRAINERS = new ArrayList<>();

    public BattleBuilder() {}

    @NotNull
    public Battle build() throws UserException {
        if (TRAINERS.isEmpty()) {
            throw new IllegalStateException("No trainers have been added to the battle");
        }

        return new Battle(SETUP, TRAINERS);
    }

    @NotNull
    public Setup getSetup() {
        return SETUP;
    }

    @NotNull
    public BattleBuilder setSetup(@NotNull Setup setup) {
        SETUP = setup;
        return this;
    }

    @NotNull
    public List<TrainerBuilder> getTrainers() {
        return TRAINERS;
    }

    @NotNull
    public BattleBuilder addTrainer(@NotNull TrainerBuilder... trainerBuilders) {
        TRAINERS.addAll(Arrays.asList(trainerBuilders));
        return this;
    }

}

package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.trainer.TrainerBuilder;
import com.github.drsmugleaf.pokemon.trainer.UserException;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public class BattleBuilder {

    private Setup SETUP = Setup.getDefault();
    private final List<TrainerBuilder> TRAINERS = new ArrayList<>();

    public BattleBuilder() {}

    public Battle build() throws UserException {
        if (TRAINERS.isEmpty()) {
            throw new IllegalStateException("No trainers have been added to the battle");
        }

        return new Battle(SETUP, TRAINERS);
    }

    public Setup getSetup() {
        return SETUP;
    }

    @Contract("_ -> this")
    public BattleBuilder setSetup(Setup setup) {
        SETUP = setup;
        return this;
    }

    public List<TrainerBuilder> getTrainers() {
        return new ArrayList<>(TRAINERS);
    }

    @Contract("_ -> this")
    public BattleBuilder addTrainer(TrainerBuilder... trainerBuilders) {
        TRAINERS.addAll(Arrays.asList(trainerBuilders));
        return this;
    }

}

package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.trainer.TrainerBuilder;
import com.github.drsmugbrain.pokemon.trainer.UserException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public class BattleBuilder {

    private Setup SETUP;
    private final List<TrainerBuilder> TRAINERS = new ArrayList<>();

    public BattleBuilder() {}

    public Battle build() throws UserException {
        return new Battle(SETUP, TRAINERS);
    }

    public Setup getSetup() {
        return SETUP;
    }

    public BattleBuilder setSetup(@Nonnull Setup setup) {
        SETUP = setup;
        return this;
    }

    public List<TrainerBuilder> getTrainers() {
        return TRAINERS;
    }

    public BattleBuilder addTrainer(@Nonnull TrainerBuilder... trainerBuilders) {
        TRAINERS.addAll(Arrays.asList(trainerBuilders));
        return this;
    }

}

package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Trainer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 20/07/2017.
 */
public class TrainerChoosingPokemonEvent extends Event {

    @Nonnull
    private final List<Trainer> TRAINERS;

    public TrainerChoosingPokemonEvent(@Nonnull Trainer... trainers) {
        super(trainers[0].getBattle());
        TRAINERS = new ArrayList<>(Arrays.asList(trainers));
    }

    public TrainerChoosingPokemonEvent(@Nonnull Collection<Trainer> trainers) {
        super(trainers.iterator().next().getBattle());
        TRAINERS = new ArrayList<>(trainers);
    }

    @Nonnull
    public List<Trainer> getTrainers() {
        return TRAINERS;
    }

}

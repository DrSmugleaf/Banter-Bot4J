package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.trainer.Trainer;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 20/07/2017.
 */
public class TrainerChoosingPokemonEvent extends Event {

    @Nonnull
    private final List<Trainer> TRAINERS;

    public TrainerChoosingPokemonEvent(@Nonnull Trainer... trainers) {
        super(trainers[0].BATTLE);
        TRAINERS = Collections.unmodifiableList(Arrays.asList(trainers));
    }

    public TrainerChoosingPokemonEvent(@Nonnull Collection<Trainer> trainers) {
        super(trainers.iterator().next().BATTLE);
        TRAINERS = Collections.unmodifiableList(new ArrayList<>(trainers));
    }

    @Nonnull
    public List<Trainer> getTrainers() {
        return TRAINERS;
    }

}

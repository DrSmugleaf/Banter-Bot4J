package com.github.drsmugleaf.pokemon.events.trainer;

import com.github.drsmugleaf.pokemon.events.Event;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * Created by DrSmugleaf on 20/07/2017.
 */
public class TrainerChoosingPokemonEvent extends Event {

    private final List<Trainer> TRAINERS;

    public TrainerChoosingPokemonEvent(Trainer... trainers) {
        super(trainers[0].BATTLE);
        TRAINERS = Collections.unmodifiableList(Arrays.asList(trainers));
    }

    public TrainerChoosingPokemonEvent(Collection<Trainer> trainers) {
        super(trainers.iterator().next().BATTLE);
        TRAINERS = Collections.unmodifiableList(new ArrayList<>(trainers));
    }

    @Contract(" -> new")
    public List<Trainer> getTrainers() {
        return new ArrayList<>(TRAINERS);
    }

}

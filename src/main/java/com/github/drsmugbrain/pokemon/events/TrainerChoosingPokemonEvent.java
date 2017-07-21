package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Trainer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 20/07/2017.
 */
public class TrainerChoosingPokemonEvent extends Event {

    private final Trainer[] TRAINERS;

    public TrainerChoosingPokemonEvent(Trainer... trainers) {
        super(trainers[0].getBattle());
        this.TRAINERS = trainers;
    }

    public List<Trainer> getTrainers() {
        return Arrays.asList(this.TRAINERS);
    }

}

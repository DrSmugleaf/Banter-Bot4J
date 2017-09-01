package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Phase;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public abstract class PhaseChangeEvent extends Event {

    private final Phase PHASE;

    public PhaseChangeEvent(@Nonnull Game game, @Nonnull Phase phase) {
        super(game);
        this.PHASE = phase;
    }

    @Nonnull
    public Phase getPhase() {
        return this.PHASE;
    }

}

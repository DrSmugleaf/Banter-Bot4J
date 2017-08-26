package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Phase;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public abstract class PhaseChangeEvent extends Event {

    private final Phase PHASE;

    public PhaseChangeEvent(Game game, Phase phase) {
        super(game);
        this.PHASE = phase;
    }

    public Phase getPhase() {
        return this.PHASE;
    }

}

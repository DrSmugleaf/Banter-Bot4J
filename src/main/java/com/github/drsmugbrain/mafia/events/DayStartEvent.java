package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Phase;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public class DayStartEvent extends PhaseChangeEvent {

    public DayStartEvent(Game game, Phase phase) {
        super(game, phase);
    }

}

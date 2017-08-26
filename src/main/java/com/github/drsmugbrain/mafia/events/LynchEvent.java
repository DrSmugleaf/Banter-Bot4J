package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Phase;
import com.github.drsmugbrain.mafia.Player;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public abstract class LynchEvent extends PhaseChangeEvent {

    private final Player PLAYER;

    public LynchEvent(Game game, Player player) {
        super(game, Phase.LYNCH);
        this.PLAYER = player;
    }

    public Player getPlayer() {
        return this.PLAYER;
    }

}

package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Phase;
import com.github.drsmugbrain.mafia.Player;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public abstract class TrialEvent extends PhaseChangeEvent {

    private final Player PLAYER;

    public TrialEvent(Game game, Player player) {
        super(game, Phase.TRIAL);
        this.PLAYER = player;
    }

    public Player getPlayer() {
        return this.PLAYER;
    }

}

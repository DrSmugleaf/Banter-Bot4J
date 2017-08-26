package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public class LynchExecuteEvent extends LynchEvent {

    public LynchExecuteEvent(Game game, Player player) {
        super(game, player);
    }

}

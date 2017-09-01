package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public class StatusEvent extends MessageEvent {

    public StatusEvent(Game game, Player player, String message) {
        super(game, player, message);
    }

}

package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public abstract class MessageEvent extends PlayerEvent {

    private final String MESSAGE;

    public MessageEvent(Game game, Player player, String message) {
        super(game, player);
        this.MESSAGE = message;
    }

    public String getMessage() {
        return this.MESSAGE;
    }

}

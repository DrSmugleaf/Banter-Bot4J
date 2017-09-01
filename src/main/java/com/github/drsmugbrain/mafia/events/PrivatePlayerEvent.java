package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public class PrivatePlayerEvent extends Event {

    private final Player PLAYER;
    private final String MESSAGE;

    public PrivatePlayerEvent(Game game, Player player, String message) {
        super(game);
        this.PLAYER = player;
        this.MESSAGE = message;
    }

    public Player getPlayer() {
        return this.PLAYER;
    }

    public String getMessage() {
        return this.MESSAGE;
    }

}

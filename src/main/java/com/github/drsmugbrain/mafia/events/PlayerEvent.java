package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public abstract class PlayerEvent extends Event {

    private final Player PLAYER;

    public PlayerEvent(@Nonnull Game game, Player player) {
        super(game);
        this.PLAYER = player;
    }

    public Player getPlayer() {
        return this.PLAYER;
    }

}

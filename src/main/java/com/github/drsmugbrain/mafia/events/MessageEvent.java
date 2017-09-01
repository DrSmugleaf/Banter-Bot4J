package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public abstract class MessageEvent extends PlayerEvent {

    private final String MESSAGE;

    public MessageEvent(@Nonnull Game game, @Nonnull Player player, @Nonnull String message) {
        super(game, player);
        this.MESSAGE = message;
    }

    public String getMessage() {
        return this.MESSAGE;
    }

}

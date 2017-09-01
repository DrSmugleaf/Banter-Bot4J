package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public class StatusEvent extends MessageEvent {

    public StatusEvent(@Nonnull Game game, @Nonnull Player player, @Nonnull String message) {
        super(game, player, message);
    }

}

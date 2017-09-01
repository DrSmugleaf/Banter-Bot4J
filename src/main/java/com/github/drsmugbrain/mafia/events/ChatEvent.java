package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public class ChatEvent extends MessageEvent {

    public ChatEvent(@Nonnull Game game, Player player, String message) {
        super(game, player, message);
    }

}

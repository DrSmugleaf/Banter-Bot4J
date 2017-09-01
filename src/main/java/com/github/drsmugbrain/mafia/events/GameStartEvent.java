package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public class GameStartEvent extends Event {

    public GameStartEvent(@Nonnull Game game) {
        super(game);
    }

}

package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public abstract class Event {

    private final Game GAME;

    public Event(@Nonnull Game game) {
        this.GAME = game;
    }

    public Game getGame() {
        return this.GAME;
    }

}

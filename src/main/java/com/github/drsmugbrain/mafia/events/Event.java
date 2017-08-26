package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public abstract class Event {

    private final Game GAME;

    public Event(Game game) {
        this.GAME = game;
    }

    public Game getGame() {
        return this.GAME;
    }

}

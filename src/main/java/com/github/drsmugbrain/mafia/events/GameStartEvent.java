package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public class GameStartEvent extends Event {

    public GameStartEvent(Game game) {
        super(game);
    }

}

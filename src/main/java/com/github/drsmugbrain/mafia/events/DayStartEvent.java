package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public class DayStartEvent extends DayEvent {

    public DayStartEvent(@Nonnull Game game) {
        super(game);
    }

}

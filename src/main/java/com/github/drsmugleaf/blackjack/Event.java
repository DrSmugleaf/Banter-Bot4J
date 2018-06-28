package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public abstract class Event {

    @Nonnull
    public final Game GAME;

    Event(@Nonnull Game game) {
        GAME = game;
    }

}

package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 30/06/2018
 */
public abstract class PlayerEvent extends Event {

    @Nonnull
    public final Player PLAYER;

    PlayerEvent(@Nonnull Game game, @Nonnull Player player) {
        super(game);
        PLAYER = player;
    }

}

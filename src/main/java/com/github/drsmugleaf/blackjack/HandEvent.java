package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/07/2018
 */
public abstract class HandEvent extends PlayerEvent {

    @Nonnull
    public final Hand HAND;

    HandEvent(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {
        super(game, player);
        HAND = hand;
    }

}

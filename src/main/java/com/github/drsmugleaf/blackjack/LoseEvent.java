package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 29/06/2018
 */
public class LoseEvent extends PlayerEvent {

    LoseEvent(@Nonnull Game game, @Nonnull Player player) {
        super(game, player);
    }

}

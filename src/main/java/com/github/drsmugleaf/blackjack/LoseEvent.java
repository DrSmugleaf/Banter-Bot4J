package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 29/06/2018
 */
public class LoseEvent extends HandEvent {

    LoseEvent(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {
        super(game, player, hand);
    }

}

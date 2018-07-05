package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 30/06/2018
 */
public class TieEvent extends HandEvent {

    TieEvent(@Nonnull Game game, @Nonnull Player player, @Nonnull Hand hand) {
        super(game, player, hand);
    }

}

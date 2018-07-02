package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 29/06/2018
 */
public class EndRoundEvent extends Event {

    EndRoundEvent(@Nonnull Game game) {
        super(game);
    }

}

package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/07/2018
 */
public class TurnStartEvent extends Event {

    TurnStartEvent(@Nonnull Game game) {
        super(game);
    }

}

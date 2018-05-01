package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Player {

    final long ID;

    @Nonnull
    final Hand HAND = new Hand();

    @Nonnull
    Actions action = Actions.NONE;

    @Nonnull
    Status status = Status.WAITING;

    Player(long id) {
        ID = id;
    }

}

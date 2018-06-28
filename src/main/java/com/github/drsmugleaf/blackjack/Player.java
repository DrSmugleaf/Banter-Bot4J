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
    private Actions action = Actions.NONE;

    @Nonnull
    private Status status = Status.WAITING;

    Player(long id) {
        ID = id;
    }

    @Nonnull
    public Actions getAction() {
        return action;
    }

    public boolean setAction(@Nonnull Actions action) {
        if (action.isValidFor(this)) {
            this.action = action;
            return true;
        } else {
            return false;
        }
    }

    @Nonnull
    public Status getStatus() {
        return status;
    }

    void setStatus(@Nonnull Status status) {
        this.status = status;
    }

}

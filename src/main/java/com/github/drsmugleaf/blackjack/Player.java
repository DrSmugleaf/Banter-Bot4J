package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.Set;

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

    public Set<Actions> getAvailableActions() {
        EnumSet<Actions> actions = EnumSet.noneOf(Actions.class);
        for (Actions action : Actions.values()) {
            if (action.isValidFor(this)) {
                actions.add(action);
            }
        }

        return actions;
    }

    public boolean setAction(@Nonnull String actionName) {
        Actions action = Actions.getAction(actionName);
        if (action != null && status == Status.PLAYING && action.isValidFor(this)) {
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

    void reset() {
        action = Actions.NONE;
        status = Status.PLAYING;
        HAND.reset();
    }

}

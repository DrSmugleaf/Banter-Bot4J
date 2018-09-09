package com.github.drsmugleaf.pokemon.status;

import com.github.drsmugleaf.pokemon.battle.Action;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 03/07/2017.
 */
public class VolatileStatus {

    public final BaseVolatileStatus BASE_VOLATILE_STATUS;
    public final Action ACTION;
    public final int DURATION;

    protected VolatileStatus(
            @Nonnull BaseVolatileStatus baseVolatileStatus,
            @Nonnull Action action,
            int duration
    ) {
        BASE_VOLATILE_STATUS = baseVolatileStatus;
        ACTION = action;
        DURATION = duration;
    }

    protected VolatileStatus(
            @Nonnull BaseVolatileStatus baseVolatileStatus,
            @Nonnull Action action
    ) {
        this(baseVolatileStatus, action, baseVolatileStatus.getDuration(action));
    }

    @Nonnull
    public BaseVolatileStatus getBaseVolatileStatus() {
        return BASE_VOLATILE_STATUS;
    }

    @Nonnull
    public Action getAction() {
        return ACTION;
    }

    public int getDuration() {
        return DURATION;
    }

}

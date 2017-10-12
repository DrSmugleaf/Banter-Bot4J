package com.github.drsmugbrain.pokemon.status;

import com.github.drsmugbrain.pokemon.Action;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 03/07/2017.
 */
public class VolatileStatus {

    private final BaseVolatileStatus BASE_VOLATILE_STATUS;
    private final Action ACTION;
    private final int DURATION;

    public VolatileStatus(
            @Nonnull BaseVolatileStatus baseVolatileStatus,
            @Nonnull Action action,
            int duration
    ) {
        BASE_VOLATILE_STATUS = baseVolatileStatus;
        ACTION = action;
        DURATION = duration;
    }

    public BaseVolatileStatus getBaseVolatileStatus() {
        return BASE_VOLATILE_STATUS;
    }

    public Action getAction() {
        return ACTION;
    }

    public int getDuration() {
        return DURATION;
    }

}

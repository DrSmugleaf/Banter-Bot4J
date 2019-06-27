package com.github.drsmugleaf.pokemon.status;

import com.github.drsmugleaf.pokemon.battle.Action;

/**
 * Created by DrSmugleaf on 03/07/2017.
 */
public class VolatileStatus {

    public final BaseVolatileStatus BASE_VOLATILE_STATUS;
    public final Action ACTION;
    public final int DURATION;

    protected VolatileStatus(
            BaseVolatileStatus baseVolatileStatus,
            Action action,
            int duration
    ) {
        BASE_VOLATILE_STATUS = baseVolatileStatus;
        ACTION = action;
        DURATION = duration;
    }

    protected VolatileStatus(
            BaseVolatileStatus baseVolatileStatus,
            Action action
    ) {
        this(baseVolatileStatus, action, baseVolatileStatus.getDuration(action));
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

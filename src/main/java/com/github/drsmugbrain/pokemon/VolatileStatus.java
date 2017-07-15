package com.github.drsmugbrain.pokemon;

/**
 * Created by DrSmugleaf on 03/07/2017.
 */
public class VolatileStatus {

    private final BaseVolatileStatus BASE_VOLATILE_STATUS;
    private final Pokemon APPLIER;
    private final int DURATION;

    protected VolatileStatus(BaseVolatileStatus baseVolatileStatus, Pokemon applier, int duration) {
        this.BASE_VOLATILE_STATUS = baseVolatileStatus;
        this.APPLIER = applier;
        this.DURATION = duration;
    }

    public BaseVolatileStatus getBaseVolatileStatus() {
        return this.BASE_VOLATILE_STATUS;
    }

    public Pokemon getApplier() {
        return this.APPLIER;
    }

    public int getDuration() {
        return this.DURATION;
    }

}

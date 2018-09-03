package com.github.drsmugleaf.music;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public abstract class HandlerEvent extends Event {

    private final AudioResultHandler HANDLER;

    protected HandlerEvent(@Nonnull AudioResultHandler handler) {
        HANDLER = handler;
    }

    public AudioResultHandler getHandler() {
        return HANDLER;
    }

}

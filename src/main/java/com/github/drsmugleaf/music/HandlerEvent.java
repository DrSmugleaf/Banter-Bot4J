package com.github.drsmugleaf.music;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public abstract class HandlerEvent extends Event {

    private final AudioResultHandler HANDLER;

    protected HandlerEvent(AudioResultHandler handler) {
        HANDLER = handler;
    }

    public AudioResultHandler getHandler() {
        return HANDLER;
    }

}

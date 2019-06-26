package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class LoadFailedEvent extends HandlerEvent {

    private final FriendlyException EXCEPTION;

    protected LoadFailedEvent(AudioResultHandler handler, FriendlyException exception) {
        super(handler);
        EXCEPTION = exception;
    }

    public FriendlyException getException() {
        return EXCEPTION;
    }

}

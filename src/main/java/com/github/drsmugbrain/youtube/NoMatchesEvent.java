package com.github.drsmugbrain.youtube;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class NoMatchesEvent extends HandlerEvent {

    protected NoMatchesEvent(@Nonnull AudioResultHandler handler) {
        super(handler);
    }

}

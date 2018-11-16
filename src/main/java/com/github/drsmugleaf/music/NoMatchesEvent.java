package com.github.drsmugleaf.music;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class NoMatchesEvent extends HandlerEvent {

    protected NoMatchesEvent(@NotNull AudioResultHandler handler) {
        super(handler);
    }

}

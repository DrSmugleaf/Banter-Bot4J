package com.github.drsmugleaf.music;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 15/01/2018.
 */
public class AudioSearchResultHandler extends AudioResultHandler {

    protected AudioSearchResultHandler(@NotNull IChannel channel, @NotNull IUser submitter, @NotNull String searchString) {
        super(channel, submitter, searchString);
    }

    @Override
    public void noMatches() {
        Event event = new NoMatchesEvent(this);
        EventDispatcher.dispatch(event);
    }
}

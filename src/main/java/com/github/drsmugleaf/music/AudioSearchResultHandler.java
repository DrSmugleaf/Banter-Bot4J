package com.github.drsmugleaf.music;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 15/01/2018.
 */
public class AudioSearchResultHandler extends AudioResultHandler {

    protected AudioSearchResultHandler(@Nonnull IChannel channel, @Nonnull IUser submitter, @Nonnull String searchString) {
        super(channel, submitter, searchString);
    }

    @Override
    public void noMatches() {
        Event event = new NoMatchesEvent(this);
        EventDispatcher.dispatch(event);
    }
}

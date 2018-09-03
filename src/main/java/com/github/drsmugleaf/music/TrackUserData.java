package com.github.drsmugleaf.music;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 02/03/2018.
 */
public class TrackUserData {

    @Nonnull
    public final IChannel CHANNEL;

    @Nonnull
    public final IUser SUBMITTER;

    protected TrackUserData(@Nonnull IChannel channel, @Nonnull IUser submitter) {
        CHANNEL = channel;
        SUBMITTER = submitter;
    }

}

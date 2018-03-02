package com.github.drsmugleaf.youtube;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 02/03/2018.
 */
public class TrackInfo {

    @Nonnull
    public final IChannel CHANNEL;

    @Nonnull
    public final IUser SUBMITTER;

    protected TrackInfo(@Nonnull IChannel channel, @Nonnull IUser submitter) {
        CHANNEL = channel;
        SUBMITTER = submitter;
    }

}

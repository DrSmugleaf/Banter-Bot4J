package com.github.drsmugleaf.music;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 02/03/2018.
 */
public class TrackUserData {

    @NotNull
    public final IChannel CHANNEL;

    @NotNull
    public final IUser SUBMITTER;

    protected TrackUserData(@NotNull IChannel channel, @NotNull IUser submitter) {
        CHANNEL = channel;
        SUBMITTER = submitter;
    }

}

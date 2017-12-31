package com.github.drsmugleaf.youtube;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/09/2017.
 */
public class Song {

    private final AudioTrack TRACK;
    private final IChannel CHANNEL;
    private final IUser SUBMITTER;

    protected Song(@Nonnull AudioTrack track, @Nonnull IChannel channel, @Nonnull IUser submitter) {
        this.TRACK = track;
        this.CHANNEL = channel;
        this.SUBMITTER = submitter;
    }

    public AudioTrack getTrack() {
        return this.TRACK;
    }

    public IGuild getGuild() {
        return this.CHANNEL.getGuild();
    }

    public IChannel getChannel() {
        return this.CHANNEL;
    }

    public IUser getSubmitter() {
        return this.SUBMITTER;
    }

}

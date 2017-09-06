package com.github.drsmugbrain.youtube;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/09/2017.
 */
public class Song {

    private final AudioTrack TRACK;
    private final long SUBMITTER_ID;

    protected Song(@Nonnull AudioTrack track, long submitterID) {
        this.TRACK = track;
        this.SUBMITTER_ID = submitterID;
    }

    protected AudioTrack getTrack() {
        return this.TRACK;
    }

    public long getSubmitterID() {
        return this.SUBMITTER_ID;
    }

}

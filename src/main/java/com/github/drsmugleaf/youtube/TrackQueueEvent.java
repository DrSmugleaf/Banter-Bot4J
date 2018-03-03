package com.github.drsmugleaf.youtube;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class TrackQueueEvent extends TrackEvent {

    protected TrackQueueEvent(@Nonnull AudioTrack track) {
        super(track);
    }

}

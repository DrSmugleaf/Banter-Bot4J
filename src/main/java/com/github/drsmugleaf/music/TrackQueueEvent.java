package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class TrackQueueEvent extends TrackEvent {

    protected TrackQueueEvent(@NotNull AudioTrack track) {
        super(track);
    }

}

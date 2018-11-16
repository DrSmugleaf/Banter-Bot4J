package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import org.jetbrains.annotations.Nullable;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class TrackStartEvent extends TrackEvent {

    protected TrackStartEvent(@Nullable AudioTrack track) {
        super(track);
    }

}

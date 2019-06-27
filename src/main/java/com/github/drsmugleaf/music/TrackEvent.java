package com.github.drsmugleaf.music;

import com.github.drsmugleaf.Nullable;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public abstract class TrackEvent extends Event {

    @Nullable
    public final AudioTrack TRACK;

    protected TrackEvent(@Nullable AudioTrack track) {
        super();
        TRACK = track;
    }

}

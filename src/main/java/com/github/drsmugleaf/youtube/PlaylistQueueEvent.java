package com.github.drsmugleaf.youtube;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class PlaylistQueueEvent extends Event {

    public final List<AudioTrack> TRACKS;

    protected PlaylistQueueEvent(@Nonnull List<AudioTrack> tracks) {
        TRACKS = tracks;
    }

    public List<AudioTrack> getTracks() {
        return TRACKS;
    }

}

package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class PlaylistQueueEvent extends Event {

    public final List<AudioTrack> TRACKS;

    protected PlaylistQueueEvent(@NotNull List<AudioTrack> tracks) {
        TRACKS = tracks;
    }

    public List<AudioTrack> getTracks() {
        return TRACKS;
    }

}

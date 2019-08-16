package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class TrackQueueEvent extends TrackEvent {

    protected TrackQueueEvent(AudioTrack track) {
        super(track);
    }

}

package com.github.drsmugbrain.youtube;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class PlaylistQueueEvent extends Event {

    private final List<Song> SONGS;

    protected PlaylistQueueEvent(@Nonnull List<Song> songs) {
        this.SONGS = songs;
    }

    public List<Song> getSongs() {
        return this.SONGS;
    }

}

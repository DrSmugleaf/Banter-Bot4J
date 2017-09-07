package com.github.drsmugbrain.youtube;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class PlaylistQueueEvent extends HandlerEvent {

    private final List<Song> SONGS;

    protected PlaylistQueueEvent(@Nonnull AudioResultHandler handler, @Nonnull List<Song> songs) {
        super(handler);
        this.SONGS = songs;
    }

    public List<Song> getSongs() {
        return this.SONGS;
    }

}

package com.github.drsmugleaf.youtube;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public abstract class SongEvent extends Event {

    private final Song SONG;

    protected SongEvent(@Nonnull Song song) {
        super();
        this.SONG = song;
    }

    public Song getSong() {
        return this.SONG;
    }

}

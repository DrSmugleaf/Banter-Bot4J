package com.github.drsmugleaf.youtube;

import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public abstract class SongEvent extends Event {

    @Nullable
    private final Song SONG;

    protected SongEvent(@Nullable Song song) {
        super();
        this.SONG = song;
    }

    @Nullable
    public Song getSong() {
        return this.SONG;
    }

}

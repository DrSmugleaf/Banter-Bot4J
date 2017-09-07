package com.github.drsmugbrain.youtube;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public class SongStartEvent extends SongEvent {

    protected SongStartEvent(@Nonnull Song song) {
        super(song);
    }

}

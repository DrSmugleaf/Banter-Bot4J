package com.github.drsmugbrain.youtube;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public abstract class SongEvent extends Event {

    private final Song SONG;

    protected SongEvent(@Nonnull AudioPlayer player, @Nonnull Song song) {
        super(player);
        this.SONG = song;
    }

    public Song getSong() {
        return this.SONG;
    }

}

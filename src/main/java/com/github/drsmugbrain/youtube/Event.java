package com.github.drsmugbrain.youtube;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/09/2017.
 */
public abstract class Event {

    private final AudioPlayer PLAYER;

    protected Event(@Nonnull AudioPlayer player) {
        this.PLAYER = player;
    }

    public AudioPlayer getPlayer() {
        return this.PLAYER;
    }
}

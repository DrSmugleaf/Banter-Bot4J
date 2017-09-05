package com.github.drsmugbrain.youtube;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class GuildMusicManager {

    private final AudioPlayer PLAYER;
    private final AudioProvider PROVIDER;
    private final TrackScheduler SCHEDULER;

    public GuildMusicManager(@Nonnull AudioPlayerManager manager) {
        this.PLAYER = manager.createPlayer();
        this.PROVIDER = new AudioProvider(this.PLAYER);
        this.SCHEDULER = new TrackScheduler(this.PLAYER);
        this.PLAYER.addListener(this.SCHEDULER);
    }

    @Nonnull
    public AudioProvider getProvider() {
        return this.PROVIDER;
    }

    @Nonnull
    public TrackScheduler getScheduler() {
        return this.SCHEDULER;
    }

}

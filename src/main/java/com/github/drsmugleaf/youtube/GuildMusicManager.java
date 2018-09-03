package com.github.drsmugleaf.youtube;

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
        PLAYER = manager.createPlayer();
        PLAYER.setVolume(25);
        PROVIDER = new AudioProvider(PLAYER);
        SCHEDULER = new TrackScheduler(PLAYER);
        PLAYER.addListener(SCHEDULER);
    }

    @Nonnull
    public AudioProvider getProvider() {
        return PROVIDER;
    }

    @Nonnull
    public TrackScheduler getScheduler() {
        return SCHEDULER;
    }

}

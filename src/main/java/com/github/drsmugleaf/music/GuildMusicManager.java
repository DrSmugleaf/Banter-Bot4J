package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class GuildMusicManager {

    private final AudioPlayer PLAYER;
    private final AudioProvider PROVIDER;
    private final TrackScheduler SCHEDULER;

    public GuildMusicManager(@NotNull AudioPlayerManager manager) {
        PLAYER = manager.createPlayer();
        PLAYER.setVolume(25);
        PROVIDER = new AudioProvider(PLAYER);
        SCHEDULER = new TrackScheduler(PLAYER);
        PLAYER.addListener(SCHEDULER);
    }

    @NotNull
    public AudioProvider getProvider() {
        return PROVIDER;
    }

    @NotNull
    public TrackScheduler getScheduler() {
        return SCHEDULER;
    }

}

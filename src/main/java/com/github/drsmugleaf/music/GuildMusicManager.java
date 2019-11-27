package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class GuildMusicManager {

    private final GuildAudioProvider PROVIDER;
    private final TrackScheduler SCHEDULER;

    public GuildMusicManager(AudioPlayerManager manager) {
        var player = manager.createPlayer();
        player.setVolume(15);
        PROVIDER = new GuildAudioProvider(player);
        SCHEDULER = new TrackScheduler(player);
        player.addListener(SCHEDULER);
    }

    public GuildAudioProvider getProvider() {
        return PROVIDER;
    }

    public TrackScheduler getScheduler() {
        return SCHEDULER;
    }

}

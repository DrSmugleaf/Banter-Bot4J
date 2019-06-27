package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class GuildMusicManager {

    private final AudioPlayer PLAYER;
    private final GuildAudioProvider PROVIDER;
    private final TrackScheduler SCHEDULER;

    public GuildMusicManager(AudioPlayerManager manager) {
        PLAYER = manager.createPlayer();
        PLAYER.setVolume(15);
        PROVIDER = new GuildAudioProvider(PLAYER);
        SCHEDULER = new TrackScheduler(PLAYER);
        PLAYER.addListener(SCHEDULER);
    }

    public GuildAudioProvider getProvider() {
        return PROVIDER;
    }

    public TrackScheduler getScheduler() {
        return SCHEDULER;
    }

}

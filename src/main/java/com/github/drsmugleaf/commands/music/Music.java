package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.music.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.*;

import org.jetbrains.annotations.NotNull;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
public class Music {

    @NotNull
    public static final AudioPlayerManager PLAYER_MANAGER = new DefaultAudioPlayerManager();

    @NotNull
    private static final Map<IGuild, GuildMusicManager> MUSIC_MANAGERS = new HashMap<>();

    @NotNull
    static final Cache<AbstractMap.SimpleEntry<IGuild, IUser>, List<AudioTrack>> UNDO_STOP_CACHE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    static {
        AudioSourceManagers.registerRemoteSources(PLAYER_MANAGER);
        EventDispatcher.registerListener(new Music());
    }

    public static synchronized GuildMusicManager getGuildMusicManager(IGuild guild) {
        GuildMusicManager musicManager = MUSIC_MANAGERS.computeIfAbsent(
                guild, k -> new GuildMusicManager(PLAYER_MANAGER)
        );

        guild.getAudioManager().setAudioProvider(musicManager.getProvider());

        return musicManager;
    }

    @TrackEventHandler(event = TrackStartEvent.class)
    public static void handle(@NotNull TrackStartEvent event) {
        AudioTrack track = event.TRACK;
        if (track == null) {
            return;
        }

        TrackUserData trackUserData = track.getUserData(TrackUserData.class);
        IGuild guild = trackUserData.CHANNEL.getGuild();
        IVoiceState voiceState = trackUserData.SUBMITTER.getVoiceStateForGuild(guild);
        IVoiceChannel authorVoiceChannel = voiceState.getChannel();
        IVoiceChannel botVoiceChannel = authorVoiceChannel.getClient().getOurUser().getVoiceStateForGuild(guild).getChannel();
        if (botVoiceChannel != authorVoiceChannel) {
            authorVoiceChannel.join();
        }

        String response = String.format("Now playing `%s`.", track.getInfo().title);
        MusicCommand.sendMessage(trackUserData.CHANNEL, response);
    }

    @TrackEventHandler(event = TrackQueueEvent.class)
    public static void handle(@NotNull TrackQueueEvent event) {
        AudioTrack track = event.TRACK;
        if (track == null) {
            return;
        }

        TrackUserData trackUserData = track.getUserData(TrackUserData.class);
        String response = String.format("Added `%s` to the queue.", track.getInfo().title);
        MusicCommand.sendMessage(trackUserData.CHANNEL, response);
    }

    @TrackEventHandler(event = NoMatchesEvent.class)
    public static void handle(@NotNull NoMatchesEvent event) {
        AudioResultHandler handler = event.getHandler();
        String response = String.format("No results found for %s.", handler.getSearchString());
        MusicCommand.sendMessage(handler.getChannel(), response);
    }

    @TrackEventHandler(event = LoadFailedEvent.class)
    public static void handle(@NotNull LoadFailedEvent event) {
        String response = String.format("Error playing track: %s", event.getException().getMessage());
        MusicCommand.sendMessage(event.getHandler().getChannel(), response);
    }

    @TrackEventHandler(event = PlaylistQueueEvent.class)
    public static void handle(@NotNull PlaylistQueueEvent event) {
        List<AudioTrack> tracks = event.TRACKS;
        AudioTrack firstTrack = tracks.get(0);
        IChannel channel = firstTrack.getUserData(TrackUserData.class).CHANNEL;
        String response = String.format("Added %d tracks to the queue.", tracks.size());
        MusicCommand.sendMessage(channel, response);
    }

}

package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.youtube.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
public class Music {

    @Nonnull
    public static final AudioPlayerManager PLAYER_MANAGER = new DefaultAudioPlayerManager();

    @Nonnull
    public static final Map<IGuild, GuildMusicManager> MUSIC_MANAGERS = new HashMap<>();

    @Nonnull
    static final Cache<AbstractMap.SimpleEntry<IGuild, IUser>, List<AudioTrack>> UNDO_STOP_CACHE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Nonnull
    private static Map<IGuild, IMessage> lastMessage = new HashMap<>();

    static {
        AudioSourceManagers.registerRemoteSources(PLAYER_MANAGER);
        EventDispatcher.registerListener(new Music());
    }

    private static void sendMessage(@Nonnull IChannel channel, @Nonnull String message) {
        IMessage iMessage = Command.sendMessage(channel, message);
        IGuild guild = channel.getGuild();

        if (lastMessage.containsKey(guild)) {
            try {
                lastMessage.get(guild).delete();
            } catch (MissingPermissionsException ignored) {}
        }

        lastMessage.put(guild, iMessage);
    }

    private static void sendMessage(@Nonnull IChannel channel, @Nonnull EmbedObject embed) {
        IMessage iMessage = Command.sendMessage(channel, embed);
        IGuild guild = channel.getGuild();

        if (lastMessage.containsKey(guild)) {
            try {
                lastMessage.get(guild).delete();
            } catch (MissingPermissionsException ignored) {}
        }

        lastMessage.put(guild, iMessage);
    }

    public static synchronized GuildMusicManager getGuildMusicManager(IGuild guild) {
        GuildMusicManager musicManager = MUSIC_MANAGERS.computeIfAbsent(
                guild, k -> new GuildMusicManager(PLAYER_MANAGER)
        );

        guild.getAudioManager().setAudioProvider(musicManager.getProvider());

        return musicManager;
    }

    @TrackEventHandler(event = TrackStartEvent.class)
    public static void handle(@Nonnull TrackStartEvent event) {
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
        sendMessage(trackUserData.CHANNEL, response);
    }

    @TrackEventHandler(event = TrackQueueEvent.class)
    public static void handle(@Nonnull TrackQueueEvent event) {
        AudioTrack track = event.TRACK;
        if (track == null) {
            return;
        }

        TrackUserData trackUserData = track.getUserData(TrackUserData.class);
        String response = String.format("Added `%s` to the queue.", track.getInfo().title);
        sendMessage(trackUserData.CHANNEL, response);
    }

    @TrackEventHandler(event = NoMatchesEvent.class)
    public static void handle(@Nonnull NoMatchesEvent event) {
        AudioResultHandler handler = event.getHandler();
        String response = String.format("No results found for %s.", handler.getSearchString());
        sendMessage(handler.getChannel(), response);
    }

    @TrackEventHandler(event = LoadFailedEvent.class)
    public static void handle(@Nonnull LoadFailedEvent event) {
        String response = String.format("Error playing track: %s", event.getException().getMessage());
        sendMessage(event.getHandler().getChannel(), response);
    }

    @TrackEventHandler(event = PlaylistQueueEvent.class)
    public static void handle(@Nonnull PlaylistQueueEvent event) {
        List<AudioTrack> tracks = event.TRACKS;
        AudioTrack firstTrack = tracks.get(0);
        IChannel channel = firstTrack.getUserData(TrackUserData.class).CHANNEL;
        String response = String.format("Added %d tracks to the queue.", tracks.size());
        sendMessage(channel, response);
    }

}

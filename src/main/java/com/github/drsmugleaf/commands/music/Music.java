package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.music.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;

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
    private static final Map<Snowflake, GuildMusicManager> MUSIC_MANAGERS = new HashMap<>();

    @Nonnull
    static final Cache<AbstractMap.SimpleEntry<Guild, Member>, List<AudioTrack>> UNDO_STOP_CACHE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    static {
        AudioSourceManagers.registerRemoteSources(PLAYER_MANAGER);
        EventDispatcher.registerListener(new Music());
    }

    @Nonnull
    public static synchronized GuildMusicManager getGuildMusicManager(@Nonnull Snowflake guildId) {
        return MUSIC_MANAGERS.computeIfAbsent(guildId, k -> new GuildMusicManager(PLAYER_MANAGER));
    }

    @TrackEventHandler(event = TrackStartEvent.class)
    public static void handle(@Nonnull TrackStartEvent event) {
        AudioTrack track = event.TRACK;
        if (track == null) {
            return;
        }

        TrackUserData userData = track.getUserData(TrackUserData.class);
        userData
                .SUBMITTER
                .getVoiceState()
                .flatMap(VoiceState::getChannel)
                .zipWhen(channel -> Mono.just(Music.getGuildMusicManager(channel.getGuildId())))
                .map(tuple -> tuple.mapT2(GuildMusicManager::getProvider))
                .flatMap(tuple -> tuple.getT1().join(spec -> spec.setProvider(tuple.getT2())))
                .map(channel -> String.format("Now playing `%s`, submitted by %s.", track.getInfo().title, userData.SUBMITTER.getDisplayName()))
                .flatMap(response -> MusicCommand.sendMessage(userData.TEXT_CHANNEL, response))
                .subscribe();
    }

    @TrackEventHandler(event = TrackQueueEvent.class)
    public static void handle(@Nonnull TrackQueueEvent event) {
        AudioTrack track = event.TRACK;
        if (track == null) {
            return;
        }

        TrackUserData trackUserData = track.getUserData(TrackUserData.class);
        String submitterName = trackUserData.SUBMITTER.getDisplayName();

        String response = String.format("%s added `%s` to the queue.", submitterName, track.getInfo().title);
        MusicCommand.sendMessage(trackUserData.TEXT_CHANNEL, response).subscribe();
    }

    @TrackEventHandler(event = NoMatchesEvent.class)
    public static void handle(@Nonnull NoMatchesEvent event) {
        AudioResultHandler handler = event.getHandler();
        String response = String.format("No results found for %s.", handler.getSearchString());
        MusicCommand.sendMessage(handler.getTextChannel(), response).subscribe();
    }

    @TrackEventHandler(event = LoadFailedEvent.class)
    public static void handle(@Nonnull LoadFailedEvent event) {
        String response = String.format("Error playing track: %s", event.getException().getMessage());
        MusicCommand.sendMessage(event.getHandler().getTextChannel(), response).subscribe();
    }

    @TrackEventHandler(event = PlaylistQueueEvent.class)
    public static void handle(@Nonnull PlaylistQueueEvent event) {
        List<AudioTrack> tracks = event.TRACKS;
        AudioTrack firstTrack = tracks.get(0);
        TextChannel channel = firstTrack.getUserData(TrackUserData.class).TEXT_CHANNEL;
        String response = String.format("Added %d tracks to the queue.", tracks.size());
        MusicCommand.sendMessage(channel, response).subscribe();
    }

}

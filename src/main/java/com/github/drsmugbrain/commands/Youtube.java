package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.util.Bot;
import com.github.drsmugbrain.youtube.*;
import com.google.api.services.youtube.model.SearchResult;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import javafx.util.Pair;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 04/09/2017.
 */
public class Youtube {

    private static final AudioPlayerManager PLAYER_MANAGER = new DefaultAudioPlayerManager();
    private static final Map<IGuild, GuildMusicManager> MUSIC_MANAGERS = new HashMap<>();
    private static final Map<IGuild, List<IUser>> SKIP_VOTES = new HashMap<>();
    private static final Cache<Pair<IGuild, IUser>, List<Song>> UNDO_STOP_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    static {
        AudioSourceManagers.registerRemoteSources(Youtube.PLAYER_MANAGER);
        EventDispatcher.registerListener(new Youtube());
    }

    public static synchronized GuildMusicManager getGuildMusicManager(IGuild guild) {
        GuildMusicManager musicManager = Youtube.MUSIC_MANAGERS.computeIfAbsent(
                guild, k -> new GuildMusicManager(Youtube.PLAYER_MANAGER)
        );

        guild.getAudioManager().setAudioProvider(musicManager.getProvider());

        return musicManager;
    }

    @Command
    public static void play(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        if (guild == null) {
            Bot.sendMessage(channel, "This command must be used in a server channel.");
            return;
        }

        try {
            event.getMessage().delete();
        } catch (MissingPermissionsException ignored) {}

        IUser author = event.getAuthor();
        IVoiceChannel userVoiceChannel = author.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You must be in a voice channel to use this command.");
            return;
        }

        IUser bot = event.getClient().getOurUser();
        IVoiceChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        if (botVoiceChannel != userVoiceChannel) {
            userVoiceChannel.join();
        }

        String searchString = String.join("", args);
        SearchResult search;
        try {
            search = API.search(searchString);
        } catch (SearchErrorException e) {
            Bot.sendMessage(channel, "Error searching for `" + searchString + "`.");
            return;
        }
        if (search == null) {
            Bot.sendMessage(channel, "No results found for `" + searchString + "`.");
            return;
        }

        String videoID = search.getId().getVideoId();
        Youtube.PLAYER_MANAGER.loadItem(videoID, new AudioResultHandler(channel, author, searchString));
    }

    @Command
    public static void skip(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        if (guild == null) {
            Bot.sendMessage(channel, "This command must be used in a server channel.");
            return;
        }

        GuildMusicManager musicManager = Youtube.getGuildMusicManager(guild);
        if (musicManager.getScheduler().getCurrentSong() == null) {
            Bot.sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        IUser author = event.getAuthor();
        IChannel userVoiceChannel = author.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You aren't in a voice channel.");
            return;
        }

        IUser bot = event.getClient().getOurUser();
        IChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel != botVoiceChannel) {
            Bot.sendMessage(channel, "You aren't in the same voice channel as me.");
            return;
        }

        Youtube.SKIP_VOTES.computeIfAbsent(guild, k -> new ArrayList<>());

        if (Youtube.SKIP_VOTES.get(guild).contains(author)) {
            Bot.sendMessage(channel, "You have already voted to skip this song.");
            return;
        }

        Youtube.SKIP_VOTES.get(guild).add(author);

        List<IUser> users = botVoiceChannel.getUsersHere();
        int humanUsers = 0;
        for (IUser user : users) {
            if (!user.isBot()) {
                humanUsers++;
            }
        }

        int votes = Youtube.SKIP_VOTES.get(guild).size();
        int requiredVotes = humanUsers / 2;

        if (votes >= requiredVotes || author == musicManager.getScheduler().getCurrentSong().getSubmitter()) {
            Youtube.SKIP_VOTES.get(guild).clear();
            musicManager.getScheduler().skip();
            Bot.sendMessage(channel, "Skipped the current song.");
        } else {
            String response = String.format("Votes: %d/%d", votes, requiredVotes);
            Bot.sendMessage(channel, response);
        }
    }

    @Command
    public static void pause(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        if (guild == null) {
            Bot.sendMessage(channel, "This command must be used in a server channel.");
            return;
        }

        IUser author = event.getAuthor();
        if (!author.getPermissionsForGuild(guild).contains(Permissions.VOICE_MUTE_MEMBERS)) {
            Bot.sendMessage(channel, "You don't have permission pause the song that's currently playing.");
            return;
        }

        GuildMusicManager musicManager = Youtube.getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            Bot.sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        IChannel userVoiceChannel = author.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You aren't in a voice channel.");
            return;
        }

        IUser bot = event.getClient().getOurUser();
        IChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel != botVoiceChannel) {
            Bot.sendMessage(channel, "You aren't in the same voice channel as me.");
            return;
        }

        if (musicManager.getScheduler().isPaused()) {
            Bot.sendMessage(channel, "The current song is already paused. Use " + Bot.BOT_PREFIX + "resume to resume it.");
            return;
        }

        musicManager.getScheduler().pause();
        Bot.sendMessage(channel, "Paused the current song.");
    }

    @Command
    public static void resume(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        if (guild == null) {
            Bot.sendMessage(channel, "This command must be used in a server channel.");
            return;
        }

        IUser author = event.getAuthor();
        if (!author.getPermissionsForGuild(guild).contains(Permissions.VOICE_MUTE_MEMBERS)) {
            Bot.sendMessage(channel, "You don't have permission pause the song that's currently playing.");
            return;
        }

        GuildMusicManager musicManager = Youtube.getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            Bot.sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        IChannel userVoiceChannel = author.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You aren't in a voice channel.");
            return;
        }

        IUser bot = event.getClient().getOurUser();
        IChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel != botVoiceChannel) {
            Bot.sendMessage(channel, "You aren't in the same voice channel as me.");
            return;
        }

        if (!musicManager.getScheduler().isPaused()) {
            Bot.sendMessage(channel, "There isn't a song currently paused.");
            return;
        }

        musicManager.getScheduler().resume();
        Bot.sendMessage(channel, "Resumed the current song.");
    }

    @Command
    public static void stop(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        if (guild == null) {
            Bot.sendMessage(channel, "This command must be used in a server channel.");
            return;
        }

        IUser author = event.getAuthor();
        if (!author.getPermissionsForGuild(guild).contains(Permissions.VOICE_MUTE_MEMBERS)) {
            Bot.sendMessage(channel, "You don't have permission to stop all songs currently queued.");
            return;
        }

        GuildMusicManager musicManager = Youtube.getGuildMusicManager(guild);
        if (musicManager.getScheduler().getCurrentSong() == null) {
            Bot.sendMessage(channel, "There aren't any songs currently playing or in the queue.");
            return;
        }

        TrackScheduler scheduler = Youtube.getGuildMusicManager(guild).getScheduler();
        Pair<IGuild, IUser> pair = new Pair<>(guild, author);
        Youtube.UNDO_STOP_CACHE.put(pair, scheduler.getQueue());
        scheduler.stop();
        Bot.sendMessage(
                channel,
                "Stopped and removed all songs from the queue.\n" +
                "You have one minute to restore them back to the queue using " + Bot.BOT_PREFIX + "undostop."
        );
    }

    @Command
    public static void undostop(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        if (guild == null) {
            Bot.sendMessage(channel, "This command must be used in a server channel.");
            return;
        }

        IUser author = event.getAuthor();
        Pair<IGuild, IUser> pair = new Pair<>(guild, author);
        List<Song> songs = Youtube.UNDO_STOP_CACHE.getIfPresent(pair);
        if (songs == null) {
            Bot.sendMessage(channel, "You haven't stopped any songs in the last minute.");
            return;
        }

        Youtube.UNDO_STOP_CACHE.invalidate(pair);
        TrackScheduler scheduler = Youtube.getGuildMusicManager(guild).getScheduler();
        for (Song song : songs) {
            scheduler.queue(song);
        }
        Bot.sendMessage(channel, "Restored all stopped songs.");
    }

    @SongEventHandler(event = SongStartEvent.class)
    public static void handle(@Nonnull SongStartEvent event) {
        Song song = event.getSong();
        String response = String.format("Now playing `%s`.", song.getTrack().getInfo().title);
        Bot.sendMessage(song.getChannel(), response);
    }

    @SongEventHandler(event = SongQueueEvent.class)
    public static void handle(@Nonnull SongQueueEvent event) {
        Song song = event.getSong();
        String response = String.format("Added `%s` to the queue.", song.getTrack().getInfo().title);
        Bot.sendMessage(song.getChannel(), response);
    }

    @SongEventHandler(event = NoMatchesEvent.class)
    public static void handle(@Nonnull NoMatchesEvent event) {
        AudioResultHandler handler = event.getHandler();
        String response = String.format("No results found for %s.", handler.getSearchString());
        Bot.sendMessage(handler.getChannel(), response);
    }

    @SongEventHandler(event = LoadFailedEvent.class)
    public static void handle(@Nonnull LoadFailedEvent event) {
        String response = String.format("Error playing song: %s", event.getException().getMessage());
        Bot.sendMessage(event.getHandler().getChannel(), response);
    }

    @SongEventHandler(event = PlaylistQueueEvent.class)
    public static void handle(@Nonnull PlaylistQueueEvent event) {
        String response = String.format("Added %d songs to the queue.", event.getSongs().size());
        Bot.sendMessage(event.getHandler().getChannel(), response);
    }

}

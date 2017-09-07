package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.util.Bot;
import com.github.drsmugbrain.youtube.*;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 04/09/2017.
 */
public class Youtube {

    private static final AudioPlayerManager PLAYER_MANAGER = new DefaultAudioPlayerManager();
    private static final Map<IGuild, GuildMusicManager> MUSIC_MANAGERS = new HashMap<>();
    private static final Map<IGuild, List<IUser>> SKIP_VOTES = new HashMap<>();

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
        IUser author = event.getAuthor();
        IUser bot = event.getClient().getOurUser();
        if (guild == null) {
            Bot.sendMessage(channel, "The play command must be used in a server channel.");
            return;
        }

        try {
            event.getMessage().delete();
        } catch (MissingPermissionsException ignored) {}

        IVoiceChannel userVoiceChannel = author.getVoiceStateForGuild(guild).getChannel();
        IVoiceChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You must be in a voice channel to use this command.");
            return;
        }
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
        IUser author = event.getAuthor();
        IUser bot = event.getClient().getOurUser();
        GuildMusicManager musicManager = Youtube.getGuildMusicManager(guild);

        if (!musicManager.getScheduler().isPlaying()) {
            Bot.sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        IChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You aren't in a voice channel.");
            return;
        }

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

        if (votes >= requiredVotes) {
            Youtube.SKIP_VOTES.clear();
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
        IUser author = event.getAuthor();
        IUser bot = event.getClient().getOurUser();
        GuildMusicManager musicManager = Youtube.getGuildMusicManager(guild);

        if (!musicManager.getScheduler().isPlaying()) {
            Bot.sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        IChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You aren't in a voice channel.");
            return;
        }

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
        IUser author = event.getAuthor();
        IUser bot = event.getClient().getOurUser();
        GuildMusicManager musicManager = Youtube.getGuildMusicManager(guild);

        if (!musicManager.getScheduler().isPlaying()) {
            Bot.sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        IChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You aren't in a voice channel.");
            return;
        }

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
        IUser author = event.getAuthor();
        IUser bot = event.getClient().getOurUser();
        TrackScheduler scheduler = Youtube.getGuildMusicManager(guild).getScheduler();
        Song currentSong = scheduler.getCurrentSong();

        if (currentSong == null) {
            Bot.sendMessage(channel, "There isn't a song currently playing.");
            return;
        }

        if (scheduler.getCurrentSong().getSubmitter() != author) {
            Bot.sendMessage(channel, "You don't have permission to stop the song that's currently playing.");
            return;
        }

        IChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel == null) {
            Bot.sendMessage(channel, "You aren't in a voice channel.");
            return;
        }

        IChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        if (userVoiceChannel != botVoiceChannel) {
            Bot.sendMessage(channel, "You aren't in the same voice channel as me.");
            return;
        }

        scheduler.skip();
        Bot.sendMessage(channel, "Stopped the current song.");
    }

    @SongEventHandler(event = SongStartEvent.class)
    public static void handle(@Nonnull SongStartEvent event) {
        String response = String.format("Now playing `%s`.", event.getSong().getTrack().getInfo().title);
        Bot.sendMessage(event.getSong().getChannel(), response);
    }

}

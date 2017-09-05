package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.util.Bot;
import com.github.drsmugbrain.youtube.API;
import com.github.drsmugbrain.youtube.GuildMusicManager;
import com.github.drsmugbrain.youtube.SearchErrorException;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.MissingPermissionsException;

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
    }

    private static synchronized GuildMusicManager getGuildMusicManager(IGuild guild) {
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
        Youtube.PLAYER_MANAGER.loadItem(videoID, new AudioLoadResultHandler() {
            GuildMusicManager musicManager = Youtube.getGuildMusicManager(guild);

            @Override
            public void trackLoaded(AudioTrack track) {
                boolean isPlaying = musicManager.getScheduler().isPlaying();

                musicManager.getScheduler().queue(track);

                String response;
                String trackTitle = track.getInfo().title;
                if (isPlaying) {
                    response = String.format("Added `%s` to the queue.", trackTitle);
                    Bot.sendMessage(channel, response);
                } else {
                    response = String.format("Now playing: `%s`.", trackTitle);
                    Bot.sendMessage(channel, response);
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                List<AudioTrack> tracks = playlist.getTracks();
                boolean isPlaying = musicManager.getScheduler().isPlaying();

                for (AudioTrack track : tracks) {
                    musicManager.getScheduler().queue(track);
                }

                String response = String.format("Added %d songs to the queue.", tracks.size());
                Bot.sendMessage(channel, response);

                if (!isPlaying) {
                    response = String.format("Now playing: `%s`.", tracks.get(0).getInfo().title);
                    Bot.sendMessage(channel, response);
                }
            }

            @Override
            public void noMatches() {
                String response = String.format("No results found for %s.", searchString);
                Bot.sendMessage(channel, response);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                String response = String.format("Error playing song: %s", exception.getMessage());
                Bot.sendMessage(channel, response);
            }
        });
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

}

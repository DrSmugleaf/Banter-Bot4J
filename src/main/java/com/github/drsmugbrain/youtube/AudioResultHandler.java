package com.github.drsmugbrain.youtube;

import com.github.drsmugbrain.commands.Youtube;
import com.github.drsmugbrain.util.Bot;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class AudioResultHandler implements AudioLoadResultHandler {

    private final GuildMusicManager MUSIC_MANAGER;
    private final IChannel CHANNEL;
    private final IUser SUBMITTER;
    private final String SEARCH_STRING;

    public AudioResultHandler(@Nonnull IChannel channel, @Nonnull IUser submitter, @Nonnull String searchString) {
        this.MUSIC_MANAGER = Youtube.getGuildMusicManager(channel.getGuild());
        this.CHANNEL = channel;
        this.SUBMITTER = submitter;
        this.SEARCH_STRING = searchString;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        boolean isPlaying = this.MUSIC_MANAGER.getScheduler().isPlaying();

        Song song = new Song(track, this.CHANNEL, this.SUBMITTER);
        this.MUSIC_MANAGER.getScheduler().queue(song);

        String response;
        String trackTitle = track.getInfo().title;
        if (isPlaying) {
            response = String.format("Added `%s` to the queue.", trackTitle);
            Bot.sendMessage(this.CHANNEL, response);
        }
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        List<AudioTrack> tracks = playlist.getTracks();

        for (AudioTrack track : tracks) {
            Song song = new Song(track, this.CHANNEL, this.SUBMITTER);
            this.MUSIC_MANAGER.getScheduler().queue(song);
        }

        String response = String.format("Added %d songs to the queue.", tracks.size());
        Bot.sendMessage(this.CHANNEL, response);
    }

    @Override
    public void noMatches() {
        String response = String.format("No results found for %s.", this.SEARCH_STRING);
        Bot.sendMessage(this.CHANNEL, response);
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        String response = String.format("Error playing song: %s", exception.getMessage());
        Bot.sendMessage(this.CHANNEL, response);
    }

}

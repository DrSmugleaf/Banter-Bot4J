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

    public IChannel getChannel() {
        return this.CHANNEL;
    }

    public IUser getSubmitter() {
        return this.SUBMITTER;
    }

    public String getSearchString() {
        return this.SEARCH_STRING;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        Song song = new Song(track, this.CHANNEL, this.SUBMITTER);
        this.MUSIC_MANAGER.getScheduler().queue(song);
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
        Event event = new NoMatchesEvent(this);
        EventDispatcher.dispatch(event);
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        Event event = new LoadFailedEvent(this, exception);
        EventDispatcher.dispatch(event);
    }

}

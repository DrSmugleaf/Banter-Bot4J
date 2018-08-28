package com.github.drsmugleaf.youtube;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.music.Music;
import com.google.api.services.youtube.model.SearchResult;
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
        MUSIC_MANAGER = Music.getGuildMusicManager(channel.getGuild());
        CHANNEL = channel;
        SUBMITTER = submitter;
        SEARCH_STRING = searchString;
    }

    public IChannel getChannel() {
        return CHANNEL;
    }

    public IUser getSubmitter() {
        return SUBMITTER;
    }

    public String getSearchString() {
        return SEARCH_STRING;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        TrackUserData trackUserData = new TrackUserData(CHANNEL, SUBMITTER);
        track.setUserData(trackUserData);
        MUSIC_MANAGER.getScheduler().queue(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        List<AudioTrack> tracks = playlist.getTracks();
        MUSIC_MANAGER.getScheduler().queue(tracks);
    }

    @Override
    public void noMatches() {
        SearchResult search;
        try {
            search = API.search(SEARCH_STRING);
        } catch (SearchErrorException e) {
            Command.sendMessage(CHANNEL, "Error searching for `" + SEARCH_STRING + "`.");
            return;
        }

        if (search == null) {
            Command.sendMessage(CHANNEL, "No results found for `" + SEARCH_STRING + "`.");
            return;
        }

        String videoID = search.getId().getVideoId();
        Music.PLAYER_MANAGER.loadItem(videoID, new AudioSearchResultHandler(CHANNEL, SUBMITTER, SEARCH_STRING));
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        Event event = new LoadFailedEvent(this, exception);
        EventDispatcher.dispatch(event);
    }

}

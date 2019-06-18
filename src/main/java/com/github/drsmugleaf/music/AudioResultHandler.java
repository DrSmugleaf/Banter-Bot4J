package com.github.drsmugleaf.music;

import com.github.drsmugleaf.commands.music.Music;
import com.github.drsmugleaf.commands.music.MusicCommand;
import com.github.drsmugleaf.music.youtube.API;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.VoiceChannel;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class AudioResultHandler implements AudioLoadResultHandler {

    private final GuildMusicManager MUSIC_MANAGER;
    private final TextChannel TEXT_CHANNEL;
    private final Member SUBMITTER;
    private final VoiceChannel VOICE_CHANNEL;
    private final String SEARCH_STRING;

    public AudioResultHandler(@Nonnull TextChannel textChannel, @Nonnull Member submitter, @Nonnull VoiceChannel voiceChannel, @Nonnull String searchString) {
        MUSIC_MANAGER = Music.getGuildMusicManager(textChannel.getGuildId());
        TEXT_CHANNEL = textChannel;
        VOICE_CHANNEL = voiceChannel;
        SUBMITTER = submitter;
        SEARCH_STRING = searchString;
    }

    public TextChannel getTextChannel() {
        return TEXT_CHANNEL;
    }

    public Member getSubmitter() {
        return SUBMITTER;
    }

    public VoiceChannel getVoiceChannel() {
        return VOICE_CHANNEL;
    }

    public String getSearchString() {
        return SEARCH_STRING;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        TrackUserData trackUserData = new TrackUserData(TEXT_CHANNEL, SUBMITTER, VOICE_CHANNEL);
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
            MusicCommand.sendMessage(TEXT_CHANNEL, "Error searching for `" + SEARCH_STRING + "`.").subscribe();
            return;
        }

        if (search == null) {
            MusicCommand.sendMessage(TEXT_CHANNEL, "No results found for `" + SEARCH_STRING + "`.").subscribe();
            return;
        }

        String videoID = search.getId().getVideoId();
        Music.PLAYER_MANAGER.loadItem(videoID, new AudioSearchResultHandler(TEXT_CHANNEL, SUBMITTER, VOICE_CHANNEL, SEARCH_STRING));
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        Event event = new LoadFailedEvent(this, exception);
        EventDispatcher.dispatch(event);
    }

}

package com.github.drsmugleaf.youtube;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class TrackScheduler extends AudioEventAdapter {

    @Nonnull
    private final AudioPlayer PLAYER;

    @Nonnull
    private final Queue<Song> QUEUE = new LinkedList<>();

    @Nullable
    private Song currentSong = null;

    public TrackScheduler(@Nonnull AudioPlayer player) {
        PLAYER = player;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        Event event = new SongStartEvent(currentSong);
        EventDispatcher.dispatch(event);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        switch (endReason) {
            case STOPPED:
            case CLEANUP:
                QUEUE.clear();
                currentSong = null;
                return;
        }

        if (endReason.mayStartNext) {
            if (hasNextSong()) {
                play(QUEUE.poll(), false);
            }
        }
    }

    @Nonnull
    public List<Song> cloneSongs() {
        List<Song> songs = new ArrayList<>();

        Song currentSong = this.currentSong;
        if (currentSong != null) {
            songs.add(new Song(currentSong.getTrack().makeClone(), currentSong.getChannel(), currentSong.getSubmitter()));
        }

        songs.addAll(QUEUE);

        return songs;
    }

    @Nonnull
    public List<Song> getQueue() {
        return new ArrayList<>(QUEUE);
    }

    @Nullable
    public Song getCurrentSong() {
        return currentSong;
    }

    public boolean hasNextSong() {
        return QUEUE.size() > 0;
    }

    public boolean isPlaying() {
        return PLAYER.getPlayingTrack() != null;
    }

    public boolean isPaused() {
        return PLAYER.isPaused();
    }

    private boolean play(@Nullable Song song, boolean noInterrupt) {
        if (!isPlaying() || !noInterrupt || song == null) {
            currentSong = song;
            if (song == null) {
                return PLAYER.startTrack(null, false);
            }
        }

        return PLAYER.startTrack(song.getTrack(), noInterrupt);
    }

    public void queue(@Nonnull Song song) {
        if (!play(song, true)) {
            Event event = new SongQueueEvent(song);
            EventDispatcher.dispatch(event);
            QUEUE.offer(song);
        }
    }

    public void queue(@Nonnull List<Song> songs) {
        if (songs.size() == 1) {
            queue(songs.get(0));
            return;
        }

        Song firstSong = songs.remove(0);
        if (!play(firstSong, true)) {
            QUEUE.offer(firstSong);
        }

        for (Song song : songs) {
            QUEUE.offer(song);
        }

        Event event = new PlaylistQueueEvent(songs);
        EventDispatcher.dispatch(event);
    }

    public void skip() {
        play(QUEUE.poll(), false);
    }

    public void pause() {
        PLAYER.setPaused(true);
    }

    public void resume() {
        PLAYER.setPaused(false);
    }

    public void stop() {
        PLAYER.stopTrack();
    }

}

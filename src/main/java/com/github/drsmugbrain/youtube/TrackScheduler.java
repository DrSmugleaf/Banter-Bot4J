package com.github.drsmugbrain.youtube;

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
        this.PLAYER = player;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        Event event = new SongStartEvent(this.currentSong);
        EventDispatcher.dispatch(event);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        this.currentSong = null;

        if (endReason.mayStartNext) {
            if (this.hasNextSong()) {
                this.play(this.QUEUE.poll(), false);
            }
        }

        switch (endReason) {
            case STOPPED:
            case CLEANUP:
                this.QUEUE.clear();
                break;
        }
    }

    @Nonnull
    public List<Song> cloneSongs() {
        List<Song> songs = new ArrayList<>();

        Song currentSong = this.currentSong;
        if (currentSong != null) {
            songs.add(new Song(currentSong.getTrack().makeClone(), currentSong.getChannel(), currentSong.getSubmitter()));
        }

        songs.addAll(this.QUEUE);

        return songs;
    }

    @Nonnull
    public List<Song> getQueue() {
        return new ArrayList<>(this.QUEUE);
    }

    @Nullable
    public Song getCurrentSong() {
        return this.currentSong;
    }

    public boolean hasNextSong() {
        return this.QUEUE.size() > 0;
    }

    public boolean isPlaying() {
        return this.PLAYER.getPlayingTrack() != null;
    }

    public boolean isPaused() {
        return this.PLAYER.isPaused();
    }

    private boolean play(@Nonnull Song song, boolean noInterrupt) {
        if (!this.isPlaying() || !noInterrupt) {
            this.currentSong = song;
        }
        return this.PLAYER.startTrack(song.getTrack(), noInterrupt);
    }

    public void queue(@Nonnull Song song) {
        if (!this.play(song, true)) {
            Event event = new SongQueueEvent(song);
            EventDispatcher.dispatch(event);
            this.QUEUE.offer(song);
        }
    }

    public void queue(@Nonnull List<Song> songs) {
        Song firstSong = songs.remove(0);
        if (!this.play(firstSong, true)) {
            this.QUEUE.offer(firstSong);
        }

        for (Song song : songs) {
            this.QUEUE.offer(song);
        }

        Event event = new PlaylistQueueEvent(songs);
        EventDispatcher.dispatch(event);
    }

    public void skip() {
        this.PLAYER.startTrack(null, false);
        if (this.hasNextSong()) {
            this.PLAYER.startTrack(this.QUEUE.poll().getTrack(), false);
        }
    }

    public void pause() {
        this.PLAYER.setPaused(true);
    }

    public void resume() {
        this.PLAYER.setPaused(false);
    }

    public void stop() {
        this.PLAYER.stopTrack();
    }

}

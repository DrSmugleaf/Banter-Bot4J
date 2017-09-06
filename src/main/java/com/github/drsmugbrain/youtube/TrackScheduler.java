package com.github.drsmugbrain.youtube;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import javax.annotation.Nullable;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer PLAYER;
    private final Queue<Song> QUEUE = new LinkedList<>();
    private Song currentSong = null;

    public TrackScheduler(@Nonnull AudioPlayer player) {
        this.PLAYER = player;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {}

    @Nullable
    public Song getCurrentSong() {
        return this.currentSong;
    }

    public boolean hasNextSong() {
        return this.QUEUE.size() > 1;
    }

    public boolean isPlaying() {
        return this.PLAYER.getPlayingTrack() != null;
    }

    public boolean isPaused() {
        return this.PLAYER.isPaused();
    }

    public void queue(@Nonnull AudioTrack track, long submitterID) {
        if (this.isPlaying()) {
            this.QUEUE.offer(new Song(track, submitterID));
        } else {
            this.PLAYER.playTrack(track);
        }
    }

    public void skip() {
        this.PLAYER.startTrack(this.QUEUE.poll().getTrack(), false);
    }

    public void pause() {
        this.PLAYER.setPaused(true);
    }

    public void resume() {
        this.PLAYER.setPaused(false);
    }

}

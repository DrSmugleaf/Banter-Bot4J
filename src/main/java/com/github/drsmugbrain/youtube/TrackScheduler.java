package com.github.drsmugbrain.youtube;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
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
    private final Queue<AudioTrack> QUEUE = new LinkedList<>();

    public TrackScheduler(@Nonnull AudioPlayer player) {
        this.PLAYER = player;
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        super.onPlayerPause(player);
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        super.onPlayerResume(player);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        super.onTrackStart(player, track);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        super.onTrackEnd(player, track, endReason);
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        super.onTrackException(player, track, exception);
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        super.onTrackStuck(player, track, thresholdMs);
    }

    public boolean isPlaying() {
        return this.PLAYER.getPlayingTrack() != null;
    }

    public boolean hasNextSong() {
        return this.QUEUE.size() > 1;
    }

    public boolean isPaused() {
        return this.PLAYER.isPaused();
    }

    public void queue(AudioTrack track) {
        boolean isPlaying = this.PLAYER.startTrack(track, true);
        if (!isPlaying) {
            this.QUEUE.offer(track);
        }
    }

    public void skip() {
        this.PLAYER.startTrack(this.QUEUE.poll(), false);
    }

    public void pause() {
        this.PLAYER.setPaused(true);
    }

    public void resume() {
        this.PLAYER.setPaused(false);
    }

}

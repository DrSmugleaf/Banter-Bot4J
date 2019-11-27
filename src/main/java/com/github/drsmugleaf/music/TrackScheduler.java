package com.github.drsmugleaf.music;

import com.github.drsmugleaf.Nullable;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer PLAYER;
    private final Queue<AudioTrack> QUEUE = new LinkedList<>();

    @Nullable
    private AudioTrack currentTrack = null;

    public TrackScheduler(AudioPlayer player) {
        PLAYER = player;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        Event event = new TrackStartEvent(currentTrack);
        EventDispatcher.dispatch(event);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        switch (endReason) {
            case STOPPED:
            case CLEANUP:
                QUEUE.clear();
                currentTrack = null;
                return;
        }

        if (endReason.mayStartNext) {
            if (hasNextTrack()) {
                play(QUEUE.poll(), false);
            }
        }
    }

    public List<AudioTrack> cloneTracks() {
        List<AudioTrack> tracks = new ArrayList<>();

        if (currentTrack != null) {
            AudioTrack currentTrackClone = currentTrack.makeClone();
            currentTrackClone.setUserData(currentTrack.getUserData(TrackUserData.class));
            tracks.add(currentTrackClone);
        }

        tracks.addAll(QUEUE);

        return tracks;
    }

    public List<AudioTrack> getQueue() {
        return new ArrayList<>(QUEUE);
    }

    @Nullable
    public AudioTrack getCurrentTrack() {
        return currentTrack;
    }

    public boolean hasNextTrack() {
        return QUEUE.size() > 0;
    }

    public boolean isPlaying() {
        return PLAYER.getPlayingTrack() != null;
    }

    public boolean isPaused() {
        return PLAYER.isPaused();
    }

    private boolean play(@Nullable AudioTrack track, boolean noInterrupt) {
        if (!isPlaying() || !noInterrupt || track == null) {
            currentTrack = track;
            if (track == null) {
                return PLAYER.startTrack(null, false);
            }
        }

        return PLAYER.startTrack(track, noInterrupt);
    }

    public void queue(AudioTrack track) {
        if (!play(track, true)) {
            Event event = new TrackQueueEvent(track);
            EventDispatcher.dispatch(event);
            QUEUE.offer(track);
        }
    }

    public void queue(List<AudioTrack> tracks) {
        if (tracks.size() == 1) {
            queue(tracks.get(0));
            return;
        }

        AudioTrack firstTrack = tracks.remove(0);
        if (!play(firstTrack, true)) {
            QUEUE.offer(firstTrack);
        }

        for (AudioTrack track : tracks) {
            QUEUE.offer(track);
        }

        Event event = new PlaylistQueueEvent(tracks);
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

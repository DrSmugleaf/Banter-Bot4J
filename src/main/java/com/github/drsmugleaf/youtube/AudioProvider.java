package com.github.drsmugleaf.youtube;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import sx.blah.discord.handle.audio.AudioEncodingType;
import sx.blah.discord.handle.audio.IAudioProvider;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class AudioProvider implements IAudioProvider {

    private final AudioPlayer AUDIO_PLAYER;
    private byte[] lastFrame;

    public AudioProvider(AudioPlayer player) {
        this.AUDIO_PLAYER = player;
    }

    @Override
    public boolean isReady() {
        AudioFrame frame = this.AUDIO_PLAYER.provide();

        if (frame != null) {
            this.lastFrame = frame.data;
            return true;
        }

        return false;
    }

    @Override
    public byte[] provide() {
        if (this.lastFrame == null) {
            this.lastFrame = this.AUDIO_PLAYER.provide().data;
        }

        byte[] data = lastFrame.clone();
        lastFrame = null;

        return data;
    }

    @Override
    public int getChannels() {
        return 2;
    }

    @Override
    public AudioEncodingType getAudioEncodingType() {
        return AudioEncodingType.OPUS;
    }

}

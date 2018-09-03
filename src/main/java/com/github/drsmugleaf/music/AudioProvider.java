package com.github.drsmugleaf.music;

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
        AUDIO_PLAYER = player;
    }

    @Override
    public boolean isReady() {
        AudioFrame frame = AUDIO_PLAYER.provide();

        if (frame != null) {
            lastFrame = frame.getData();
            return true;
        }

        return false;
    }

    @Override
    public byte[] provide() {
        if (lastFrame == null) {
            lastFrame = AUDIO_PLAYER.provide().getData();
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

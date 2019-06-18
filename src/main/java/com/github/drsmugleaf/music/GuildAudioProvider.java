package com.github.drsmugleaf.music;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import discord4j.voice.AudioProvider;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class GuildAudioProvider extends AudioProvider {

    @Nonnull
    private final AudioPlayer PLAYER;

    @Nonnull
    private final MutableAudioFrame FRAME = new MutableAudioFrame();

    public GuildAudioProvider(@Nonnull AudioPlayer player) {
        super(ByteBuffer.allocate(StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize()));
        PLAYER = player;
        FRAME.setBuffer(getBuffer());
    }

    @Override
    public boolean provide() {
        boolean provided = PLAYER.provide(FRAME);
        if (provided) {
            getBuffer().flip();
        }

        return provided;
    }

}

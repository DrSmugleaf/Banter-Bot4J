package com.github.drsmugleaf.music;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.VoiceChannel;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 02/03/2018.
 */
public class TrackUserData {

    @Nonnull
    public final TextChannel TEXT_CHANNEL;

    @Nonnull
    public final Member SUBMITTER;

    @Nonnull
    public final VoiceChannel VOICE_CHANNEL;

    protected TrackUserData(@Nonnull TextChannel textChannel, @Nonnull Member submitter, @Nonnull VoiceChannel voiceChannel) {
        TEXT_CHANNEL = textChannel;
        SUBMITTER = submitter;
        VOICE_CHANNEL = voiceChannel;
    }

}

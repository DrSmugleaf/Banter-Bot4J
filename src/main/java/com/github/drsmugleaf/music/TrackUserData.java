package com.github.drsmugleaf.music;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.VoiceChannel;

/**
 * Created by DrSmugleaf on 02/03/2018.
 */
public class TrackUserData {

    public final TextChannel TEXT_CHANNEL;
    public final Member SUBMITTER;
    public final VoiceChannel VOICE_CHANNEL;

    protected TrackUserData(TextChannel textChannel, Member submitter, VoiceChannel voiceChannel) {
        TEXT_CHANNEL = textChannel;
        SUBMITTER = submitter;
        VOICE_CHANNEL = voiceChannel;
    }

}

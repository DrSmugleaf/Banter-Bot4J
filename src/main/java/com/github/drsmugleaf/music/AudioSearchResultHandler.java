package com.github.drsmugleaf.music;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.VoiceChannel;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 15/01/2018.
 */
public class AudioSearchResultHandler extends AudioResultHandler {

    protected AudioSearchResultHandler(@Nonnull TextChannel channel, @Nonnull Member submitter, @Nonnull VoiceChannel voiceChannel, @Nonnull String searchString) {
        super(channel, submitter, voiceChannel, searchString);
    }

    @Override
    public void noMatches() {
        Event event = new NoMatchesEvent(this);
        EventDispatcher.dispatch(event);
    }
}

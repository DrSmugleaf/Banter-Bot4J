package com.github.drsmugleaf.music;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.VoiceChannel;

/**
 * Created by DrSmugleaf on 15/01/2018.
 */
public class AudioSearchResultHandler extends AudioResultHandler {

    protected AudioSearchResultHandler(TextChannel channel, Member submitter, VoiceChannel voiceChannel, String searchString) {
        super(channel, submitter, voiceChannel, searchString);
    }

    @Override
    public void noMatches() {
        Event event = new NoMatchesEvent(this);
        EventDispatcher.dispatch(event);
    }
}

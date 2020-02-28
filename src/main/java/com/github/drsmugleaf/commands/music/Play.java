package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.AudioResultHandler;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.VoiceChannel;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(
        aliases = {"e"},
        tags = {
                Tags.VOICE_ONLY,
                Tags.DELETE_COMMAND_MESSAGE
        },
        description = "Add a track to the queue"
)
public class Play extends MusicCommand {

    @Argument(position = 1, examples = "<https://www.youtube.com/watch?v=dQw4w9WgXcQ>", maxWords = Integer.MAX_VALUE)
    private String search;

    @Override
    public void run() {
        EVENT
                .getMessage()
                .getChannel()
                .cast(TextChannel.class)
                .zipWith(Mono.justOrEmpty(EVENT.getMember()))
                .zipWhen(tuple -> tuple.getT2().getVoiceState().flatMap(VoiceState::getChannel))
                .map(tuple -> Tuples.of(tuple.getT1().getT1(), tuple.getT1().getT2(), tuple.getT2()))
                .subscribe(tuple -> {
                    TextChannel textChannel = tuple.getT1();
                    Member author = tuple.getT2();
                    VoiceChannel voiceChannel = tuple.getT3();
                    Music.PLAYER_MANAGER.loadItem(search, new AudioResultHandler(textChannel, author, voiceChannel, search));
                });
    }

}

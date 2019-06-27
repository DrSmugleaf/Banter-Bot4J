package com.github.drsmugleaf.commands.music;

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
@CommandInfo(aliases = {"e"}, tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.DELETE_COMMAND_MESSAGE})
public class Play extends MusicCommand {

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

                    String searchString = String.join(" ", ARGUMENTS);

                    Music.PLAYER_MANAGER.loadItem(searchString, new AudioResultHandler(textChannel, author, voiceChannel, searchString));
                });
    }

}

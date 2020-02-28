package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.GuildCommand;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.spec.MessageCreateSpec;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by DrSmugleaf on 28/08/2018
 */
public abstract class MusicCommand extends GuildCommand {

    private static final Map<Guild, Message> LAST_MESSAGES = new HashMap<>();

    private static void deleteLastMessage(Message newMessage) {
        newMessage
                .getGuild()
                .flatMap(guild -> Mono.justOrEmpty(LAST_MESSAGES.put(guild, newMessage)))
                .flatMap(Message::delete)
                .subscribe();
    }

    public static Mono<Message> sendMessage(MessageChannel channel, String content) {
        return channel
                .createMessage(spec -> spec.setContent(content))
                .doOnNext(MusicCommand::deleteLastMessage);
    }

    @Override
    public Mono<Message> reply(Consumer<MessageCreateSpec> spec) {
        return super
                .reply(spec)
                .doOnNext(MusicCommand::deleteLastMessage);
    }

    @Override
    public Mono<Message> reply(String content) {
        return super
                .reply(content)
                .doOnNext(MusicCommand::deleteLastMessage);
    }

}

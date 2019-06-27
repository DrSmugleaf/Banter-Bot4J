package com.github.drsmugleaf.commands.api;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.MessageCreateSpec;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * Created by DrSmugleaf on 29/05/2018.
 */
public class CommandReceivedEvent extends MessageCreateEvent {

    protected CommandReceivedEvent(MessageCreateEvent event) {
        super(
                event.getClient(),
                event.getMessage(),
                event.getGuild().map(Guild::getId).map(Snowflake::asLong).block(),
                event.getMember().orElse(null)
        );
    }

    public Mono<Message> reply(Consumer<? super MessageCreateSpec> message) {
        String mention = getMember()
                .map(User::getId)
                .map(Snowflake::asLong)
                .map(id -> "<@" + id + ">, ")
                .orElse("");

        return getMessage()
                .getChannel()
                .flatMap(channel -> channel.createMessage(spec -> {
                    message.accept(spec);
                    try {
                        Field contentField = MessageCreateSpec.class.getDeclaredField("content");
                        contentField.setAccessible(true);
                        String content = (String) contentField.get(spec);
                        if (content == null) {
                            content = "";
                        }

                        contentField.set(spec, mention + content);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        throw new IllegalStateException("Error getting spec's content field. Spec: " + spec, e);
                    }
                }))
                .doOnError(e -> Command.LOGGER.error("Message could not be sent", e));
    }

    public Mono<Message> reply(String content) {
        String mention = getMember()
                .map(User::getId)
                .map(Snowflake::asLong)
                .map(id -> "<@" + id + ">, ")
                .orElse("");

        return getMessage()
                .getChannel()
                .flatMap(channel -> channel.createMessage(mention + content))
                .doOnError(e -> Command.LOGGER.error("Message could not be sent", e));
    }

}

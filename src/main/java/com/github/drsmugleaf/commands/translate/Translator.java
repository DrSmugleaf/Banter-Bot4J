package com.github.drsmugleaf.commands.translate;

import com.github.drsmugleaf.commands.api.EventListener;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.MessageDeleteEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public class Translator {

    private static final Cache<Long, TranslatedMessage> MESSAGES = CacheBuilder
            .newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    @EventListener(MessageCreateEvent.class)
    public static void handle(MessageCreateEvent event) {
        event
                .getMessage()
                .getAuthor()
                .filter(author -> !author.isBot())
                .ifPresent(author -> {
                    TranslatedMessage message = new TranslatedMessage(event);
                    message.sendTranslations();
                    MESSAGES.put(event.getMessage().getId().asLong(), message);
                });
    }

    @EventListener(MessageUpdateEvent.class)
    public static void handle(MessageUpdateEvent event) {
        event
                .getMessage()
                .filter(message -> message.getAuthor().isPresent() && !message.getAuthor().get().isBot())
                .zipWhen(message -> Mono.justOrEmpty(MESSAGES.getIfPresent(message.getId().asLong())))
                .subscribe(tuple -> tuple.getT2().updateTranslations(tuple.getT1()));
    }

    @EventListener(MessageDeleteEvent.class)
    public static void handle(MessageDeleteEvent event) {
        Mono
                .justOrEmpty(event.getMessage())
                .filter(message -> message.getAuthor().isPresent() && !message.getAuthor().get().isBot())
                .map(Message::getId)
                .map(MESSAGES::getIfPresent)
                .doOnNext(MESSAGES::invalidate)
                .flatMapMany(TranslatedMessage::delete);
    }

}

package com.github.drsmugleaf.commands.translate;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEditEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public class Translator {

    @Nonnull
    private static final Cache<Long, TranslatedMessage> MESSAGES = CacheBuilder
            .newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    @EventSubscriber
    public static void handle(@Nonnull MessageReceivedEvent event) {
        IUser author = event.getAuthor();
        if (author.isBot()) {
            return;
        }

        TranslatedMessage message = new TranslatedMessage(event);
        message.sendTranslations();
        MESSAGES.put(event.getMessageID(), message);
    }

    @EventSubscriber
    public static void handle(@Nonnull MessageEditEvent event) {
        IUser author = event.getAuthor();
        if (author.isBot()) {
            return;
        }

        IMessage message = event.getMessage();
        Long messageID = message.getLongID();
        TranslatedMessage translatedMessage = MESSAGES.getIfPresent(messageID);
        if (translatedMessage == null) {
            return;
        }

        translatedMessage.updateTranslations(message);
    }

    @EventSubscriber
    public static void handle(@Nonnull MessageDeleteEvent event) {
        IUser author = event.getAuthor();
        if (author != null && author.isBot()) {
            return;
        }

        Long messageID = event.getMessageID();
        TranslatedMessage translatedMessage = MESSAGES.getIfPresent(messageID);
        if (translatedMessage == null) {
            return;
        }

        translatedMessage.delete();
        MESSAGES.invalidate(event.getMessageID());
    }

}

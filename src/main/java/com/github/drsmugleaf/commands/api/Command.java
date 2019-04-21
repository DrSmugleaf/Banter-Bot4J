package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.converter.Result;
import com.github.drsmugleaf.commands.api.converter.TypeConverters;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import com.github.drsmugleaf.commands.api.registry.Entry;
import com.github.drsmugleaf.commands.api.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
public class Command implements ICommand {

    @Nonnull
    protected static final Logger LOGGER = LoggerFactory.getLogger(Command.class);

    @Nonnull
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

    public CommandReceivedEvent EVENT;
    public Arguments ARGUMENTS;

    protected Command() {}

    protected static void run(@Nonnull CommandSearchResult commandSearch, @Nonnull CommandReceivedEvent event) {
        Entry entry = commandSearch.getEntry();
        CommandInfo annotation = entry.getCommandInfo();

        if (annotation != null) {
            for (Tag tags : annotation.tags()) {
                tags.execute(event);
            }
        }

        Command command = entry.newInstance();
        Arguments arguments = new Arguments(commandSearch, event);
        Entry.setEventField(command, event);
        Entry.setArgsField(command, arguments);

        if (command.setArguments(entry)) {
            command.run();
        }
    }

    public static boolean isOwner(@Nonnull IUser user) {
        return BanterBot4J.OWNERS.contains(user.getLongID());
    }

    @Nonnull
    public static String getDate() {
        return DATE_FORMAT.format(Instant.now());
    }

    @Nonnull
    public CommandReceivedEvent getEvent() {
        return EVENT;
    }

    @Nonnull
    public Arguments getArguments() {
        return ARGUMENTS;
    }

    @Nonnull
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nullable String content, @Nullable EmbedObject embed) {
        if (content == null) {
            content = "";
        }

        final String finalContent = content;
        return RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(finalContent, embed);
            } catch (RateLimitException e) {
                LOGGER.error("Message could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nonnull
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nonnull String content) {
        return sendMessage(channel, content, null);
    }

    @Nonnull
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nonnull EmbedObject embed) {
        return sendMessage(channel, null, embed);
    }

    private boolean setArguments(@Nonnull Entry entry) {
        for (CommandField commandField : entry.getCommandFields()) {
            Field field = commandField.getField();
            field.setAccessible(true);

            Object def;
            try {
                def = field.get(this);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error getting command argument " + field, e);
            }

            Result result = ARGUMENTS.getArg(commandField, def);
            if (!result.isValid()) {
                EVENT.reply(result.getErrorResponse());
                return false;
            }

            try {
                field.set(this, result.getElement());
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error setting command argument " + field, e);
            }
        }

        return true;
    }

    @Nonnull
    public IMessage sendMessage(@Nonnull String content) {
        return sendMessage(EVENT.getChannel(), content);
    }

    @Nonnull
    public IMessage sendMessage(@Nonnull EmbedObject embed) {
        return sendMessage(EVENT.getChannel(), embed);
    }

    @Nonnull
    public IMessage sendMessage(@Nonnull String content, @Nonnull EmbedObject embed) {
        return sendMessage(EVENT.getChannel(), content, embed);
    }

    @Nonnull
    public IMessage sendFile(@Nonnull EmbedObject embed, @Nonnull InputStream file, @Nonnull String fileName) {
        return RequestBuffer.request(() -> {
            try {
                return EVENT.getChannel().sendFile(embed, file, fileName);
            } catch (RateLimitException e) {
                LOGGER.error("Message could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nonnull
    public IMessage sendFile(@Nonnull String content, @Nonnull InputStream file, @Nonnull String fileName) {
        return RequestBuffer.request(() -> {
            try {
                return EVENT.getChannel().sendFile(content, file, fileName);
            } catch (RateLimitException e) {
                LOGGER.error("Message could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nonnull
    public IMessage reply(@Nonnull String content) {
        return EVENT.reply(content);
    }

    @Nonnull
    public IMessage reply(@Nonnull EmbedObject embed) {
        return EVENT.reply(embed);
    }

    @Nonnull
    public IMessage reply(@Nonnull String content, @Nonnull EmbedObject embed) {
        return EVENT.reply(content, embed);
    }

    @Override
    public void run() {}

    public void registerConverters(@Nonnull TypeConverters converter) {}

}

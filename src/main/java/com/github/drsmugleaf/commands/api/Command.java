package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
public abstract class Command implements ICommand {

    @Nonnull
    protected static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    @Nonnull
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

    @Nonnull
    public final CommandReceivedEvent EVENT;

    @Nonnull
    public final Arguments ARGS;

    protected Command(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        EVENT = event;
        ARGS = args;
    }

    private boolean setArguments(@Nonnull Entry entry) {
        for (CommandField commandField : entry.getArguments()) {
            Field field = commandField.getField();
            Object arg = ARGS.getArg(commandField);
            if (arg == null) {
                reply("Invalid arguments\n" + invalidFormatResponse());
                return false;
            }

            field.setAccessible(true);
            try {
                field.set(this, arg);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error setting command argument " + field, e);
            }
        }

        return true;
    }

    protected static void run(@Nonnull CommandSearchResult commandSearch, @Nonnull CommandReceivedEvent event) {
        Entry entry = commandSearch.COMMAND;
        CommandInfo annotation = entry.getCommandInfo();

        if (annotation != null) {
            for (Tag tags : annotation.tags()) {
                tags.execute(event);
            }
        }

        Command command;
        try {
            Constructor<? extends Command> constructor = entry.getCommand().getDeclaredConstructor(CommandReceivedEvent.class, Arguments.class);
            constructor.setAccessible(true);
            Arguments arguments = new Arguments(commandSearch, event);
            command = constructor.newInstance(event, arguments);
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Error running command " + entry.getName(), e);
            return;
        } catch (NoSuchMethodException e) {
            LOGGER.error("No constructor found for command " + entry.getName(), e);
            return;
        } catch (InvocationTargetException e) {
            LOGGER.error("Error creating command instance for command " + entry.getName(), e);
            return;
        }


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
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nullable String content, @Nullable EmbedObject embed) {
        if (content == null) {
            content = "";
        }

        String finalContent = content;
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

    @Nonnull
    public String invalidFormatResponse() {
        return "";
    }

}

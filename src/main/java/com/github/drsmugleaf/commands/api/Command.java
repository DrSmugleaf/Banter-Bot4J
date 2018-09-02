package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
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
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
public abstract class Command implements ICommand {

    @Nonnull
    protected static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    @Nonnull
    public final CommandReceivedEvent EVENT;

    @Nonnull
    public final Arguments ARGS;

    protected Command(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        EVENT = event;
        ARGS = args;
    }

    protected static void run(@Nonnull CommandSearchResult commandSearch, @Nonnull CommandReceivedEvent event) {
        Class<? extends Command> commandClass = commandSearch.COMMAND;
        CommandInfo annotation = commandClass.getDeclaredAnnotation(CommandInfo.class);

        if (annotation != null) {
            for (Tag tags : annotation.tags()) {
                tags.execute(event);
            }
        }

        ICommand command;
        try {
            Constructor<? extends Command> constructor = commandClass.getDeclaredConstructor(CommandReceivedEvent.class, Arguments.class);
            constructor.setAccessible(true);
            Arguments arguments = new Arguments(commandSearch, event);
            command = constructor.newInstance(event, arguments);
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Error running command " + commandClass.getName(), e);
            return;
        } catch (NoSuchMethodException e) {
            LOGGER.error("No constructor found for command " + commandClass.getName(), e);
            return;
        } catch (InvocationTargetException e) {
            LOGGER.error("Error creating command instance for command " + commandClass.getName(), e);
            return;
        }

        command.run();
    }

    protected static boolean isOwner(@Nonnull IUser user) {
        return BanterBot4J.OWNERS.contains(user.getLongID());
    }

    @Nonnull
    public static String getName(@Nonnull Class<? extends Command> command) {
        String commandName;
        CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);

        if (annotation == null || annotation.name().isEmpty()) {
            commandName = command.getSimpleName();
        } else {
            commandName = annotation.name();
        }

        return commandName.toLowerCase();
    }

    @Nonnull
    public static List<String> getAliases(@Nonnull Class<? extends Command> command) {
        CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);

        if (annotation == null || annotation.aliases().length == 0) {
            return new ArrayList<>();
        }

        List<String> commandAliases = new ArrayList<>();
        Collections.addAll(commandAliases, annotation.aliases());
        return commandAliases;
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

}

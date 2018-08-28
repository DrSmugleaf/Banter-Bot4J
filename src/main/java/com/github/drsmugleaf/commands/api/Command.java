package com.github.drsmugleaf.commands.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import javax.annotation.Nonnull;
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
    protected static String BOT_PREFIX = "!";

    @Nonnull
    static final List<Long> OWNERS = new ArrayList<>();

    @Nonnull
    public final CommandReceivedEvent EVENT;

    @Nonnull
    public final Arguments ARGS;

    protected Command(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        EVENT = event;
        ARGS = args;
    }

    protected static <T extends ICommand> void run(@Nonnull Class<T> commandClass, @Nonnull CommandReceivedEvent event, @Nonnull String args) {
        CommandInfo annotation = commandClass.getDeclaredAnnotation(CommandInfo.class);

        if (annotation != null) {
            if (event.getGuild() != null) {
                List<Permissions> annotationPermissions = Arrays.asList(annotation.permissions());
                EnumSet<Permissions> authorPermissions = event.getAuthor().getPermissionsForGuild(event.getGuild());

                if (!annotationPermissions.isEmpty() && Collections.disjoint(authorPermissions, Arrays.asList(annotation.permissions()))) {
                    event.reply("You don't have permission to use that command.");
                    return;
                }
            }

            for (Tag tags : annotation.tags()) {
                if (!tags.isValid(event)) {
                    event.reply(tags.message());
                    return;
                }

                tags.execute(event);
            }
        }

        ICommand command;
        try {
            Constructor<T> constructor = commandClass.getDeclaredConstructor(CommandReceivedEvent.class, Arguments.class);
            constructor.setAccessible(true);
            Arguments arguments = new Arguments(args);
            command = constructor.newInstance(event, arguments);
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Error running command " + commandClass.getName(), e);
            return;
        } catch (NoSuchMethodException e) {
            LOGGER.error("No constructor found for command " + commandClass, e);
            return;
        } catch (InvocationTargetException e) {
            LOGGER.error("Error creating command instance", e);
            return;
        }

        command.run();
    }

    protected static boolean isOwner(@Nonnull IUser user) {
        return OWNERS.contains(user.getLongID());
    }

    @Nonnull
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nonnull String content) {
        return RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(content);
            } catch (RateLimitException e) {
                LOGGER.error("Message could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nonnull
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nonnull EmbedObject embed) {
        return RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(embed);
            } catch (RateLimitException e) {
                LOGGER.error("Embed could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nonnull
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nonnull String content, @Nonnull EmbedObject embed) {
        return RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(content, embed);
            } catch (RateLimitException e) {
                LOGGER.error("Embed could not be sent", e);
                throw e;
            }
        }).get();
    }

}

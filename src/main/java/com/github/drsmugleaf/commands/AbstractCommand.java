package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DrSmugleaf on 17/01/2018.
 */
public abstract class AbstractCommand {

    @Nullable
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nonnull String message) {
        return RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(message);
            } catch (DiscordException e) {
                BanterBot4J.LOGGER.error("Message could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nullable
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nonnull EmbedObject embed) {
        return RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(embed);
            } catch (DiscordException e) {
                BanterBot4J.LOGGER.error("Embed could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nullable
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nonnull String message, @Nonnull EmbedObject embed) {
        return RequestBuffer.request(() -> {
            try {
                return channel.sendMessage(message, embed);
            } catch (DiscordException e) {
                BanterBot4J.LOGGER.error("Embed could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nullable
    public static IRole getHighestRole(List<IRole> roles) {
        if (roles.isEmpty()) {
            return null;
        }

        roles.sort(Comparator.comparingInt(IRole::getPosition));
        return roles.get(roles.size() - 1);
    }

    @Nullable
    public static IRole getHighestRole(IUser user, IGuild guild) {
        List<IRole> roles = user.getRolesForGuild(guild);
        return getHighestRole(roles);
    }

}

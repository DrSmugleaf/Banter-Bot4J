package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
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

    public static void sendMessage(@Nonnull IChannel channel, @Nonnull String message) {
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                BanterBot4J.LOGGER.error("Message could not be sent", e);
                throw e;
            }
        });
    }

    public static void sendMessage(@Nonnull IChannel channel, @Nonnull EmbedObject embed) {
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(embed);
            } catch (DiscordException e) {
                BanterBot4J.LOGGER.error("Embed could not be sent", e);
                throw e;
            }
        });
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

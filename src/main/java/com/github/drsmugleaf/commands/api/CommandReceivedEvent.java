package com.github.drsmugleaf.commands.api;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DrSmugleaf on 29/05/2018.
 */
public class CommandReceivedEvent extends MessageReceivedEvent {

    @Nonnull
    protected IDiscordClient client;

    protected CommandReceivedEvent(@Nonnull IMessage message) {
        super(message);
        client = message.getClient();
    }

    protected CommandReceivedEvent(@Nonnull MessageReceivedEvent event) {
        this(event.getMessage());
    }

    @Nonnull
    @Override
    public IDiscordClient getClient() {
        return client;
    }

    @Nullable
    public static IRole getHighestRole(@Nonnull List<IRole> roles) {
        if (roles.isEmpty()) {
            return null;
        }

        roles.sort(Comparator.comparingInt(IRole::getPosition));
        return roles.get(roles.size() - 1);
    }

    @Nullable
    public static IRole getHighestRole(@Nonnull IUser user, @Nonnull IGuild guild) {
        List<IRole> roles = user.getRolesForGuild(guild);
        return getHighestRole(roles);
    }

    @Nullable
    public IRole getHighestAuthorRole() {
        IGuild guild = getGuild();
        if (guild == null) {
            return null;
        }

        List<IRole> roles = getAuthor().getRolesForGuild(guild);
        return getHighestRole(roles);
    }

    @Nonnull
    public IMessage reply(@Nullable String content, @Nullable EmbedObject embed) {
        if (content == null) {
            content = "";
        }

        String finalContent = content;
        return RequestBuffer.request(() -> {
            try {
                return getMessage().reply(finalContent, embed);
            } catch (RateLimitException e) {
                Command.LOGGER.error("Message could not be sent", e);
                throw e;
            }
        }).get();
    }

    @Nonnull
    public IMessage reply(@Nonnull String content) {
        return reply(content, null);
    }

    @Nonnull
    public IMessage reply(@Nonnull EmbedObject embed) {
        return reply(null, embed);
    }

}

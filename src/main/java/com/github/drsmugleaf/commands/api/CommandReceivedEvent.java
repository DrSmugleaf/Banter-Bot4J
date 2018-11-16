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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DrSmugleaf on 29/05/2018.
 */
public class CommandReceivedEvent extends MessageReceivedEvent {

    @NotNull
    protected IDiscordClient client;

    protected CommandReceivedEvent(@NotNull IMessage message) {
        super(message);
        client = message.getClient();
    }

    protected CommandReceivedEvent(@NotNull MessageReceivedEvent event) {
        this(event.getMessage());
    }

    @NotNull
    @Override
    public IDiscordClient getClient() {
        return client;
    }

    @Nullable
    public static IRole getHighestRole(@NotNull List<IRole> roles) {
        if (roles.isEmpty()) {
            return null;
        }

        roles.sort(Comparator.comparingInt(IRole::getPosition));
        return roles.get(roles.size() - 1);
    }

    @Nullable
    public static IRole getHighestRole(@NotNull IUser user, @NotNull IGuild guild) {
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

    @NotNull
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

    @NotNull
    public IMessage reply(@NotNull String content) {
        return reply(content, null);
    }

    @NotNull
    public IMessage reply(@NotNull EmbedObject embed) {
        return reply(null, embed);
    }

}

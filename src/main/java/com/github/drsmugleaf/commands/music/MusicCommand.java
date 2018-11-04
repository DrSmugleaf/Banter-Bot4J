package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 28/08/2018
 */
public abstract class MusicCommand extends Command {

    @Nonnull
    private static final Map<IGuild, IMessage> LAST_MESSAGES = new HashMap<>();

    protected MusicCommand(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    private static void deleteLastMessage(@Nonnull IMessage newMessage) {
        IGuild guild = newMessage.getGuild();
        IMessage lastMessage = LAST_MESSAGES.put(guild, newMessage);
        if (lastMessage == null) {
            return;
        }

        try {
            lastMessage.delete();
        } catch (MissingPermissionsException ignored) {}
    }

    @Nonnull
    public static IMessage sendMessage(@Nonnull IChannel channel, @Nullable String content, @Nullable EmbedObject embed) {
        IMessage message = Command.sendMessage(channel, content, embed);
        deleteLastMessage(message);
        return message;
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
    @Override
    public IMessage sendMessage(@Nonnull String content) {
        return MusicCommand.sendMessage(EVENT.getChannel(), content);
    }

    @Nonnull
    @Override
    public IMessage sendMessage(@Nonnull EmbedObject embed) {
        return MusicCommand.sendMessage(EVENT.getChannel(), embed);
    }

    @Nonnull
    @Override
    public IMessage sendMessage(@Nonnull String content, @Nonnull EmbedObject embed) {
        return MusicCommand.sendMessage(EVENT.getChannel(), content, embed);
    }



}

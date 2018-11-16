package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MissingPermissionsException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 28/08/2018
 */
public abstract class MusicCommand extends Command {

    @NotNull
    private static final Map<IGuild, IMessage> LAST_MESSAGES = new HashMap<>();

    protected MusicCommand(@NotNull CommandReceivedEvent event, @NotNull Arguments args) {
        super(event, args);
    }

    private static void deleteLastMessage(@NotNull IMessage newMessage) {
        IGuild guild = newMessage.getGuild();
        IMessage lastMessage = LAST_MESSAGES.put(guild, newMessage);
        if (lastMessage == null) {
            return;
        }

        try {
            lastMessage.delete();
        } catch (MissingPermissionsException ignored) {}
    }

    @NotNull
    public static IMessage sendMessage(@NotNull IChannel channel, @Nullable String content, @Nullable EmbedObject embed) {
        IMessage message = Command.sendMessage(channel, content, embed);
        deleteLastMessage(message);
        return message;
    }

    @NotNull
    public static IMessage sendMessage(@NotNull IChannel channel, @NotNull String content) {
        return sendMessage(channel, content, null);
    }

    @NotNull
    public static IMessage sendMessage(@NotNull IChannel channel, @NotNull EmbedObject embed) {
        return sendMessage(channel, null, embed);
    }

    @NotNull
    @Override
    public IMessage sendMessage(@NotNull String content) {
        return MusicCommand.sendMessage(EVENT.getChannel(), content);
    }

    @NotNull
    @Override
    public IMessage sendMessage(@NotNull EmbedObject embed) {
        return MusicCommand.sendMessage(EVENT.getChannel(), embed);
    }

    @NotNull
    @Override
    public IMessage sendMessage(@NotNull String content, @NotNull EmbedObject embed) {
        return MusicCommand.sendMessage(EVENT.getChannel(), content, embed);
    }


}

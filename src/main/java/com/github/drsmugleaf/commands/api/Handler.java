package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.database.models.Member;
import com.github.drsmugleaf.reflection.Reflection;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Handler {

    @Nonnull
    private final Registry COMMAND_REGISTRY;

    public Handler(@Nonnull String commandsPackageName) {
        Reflection reflection = new Reflection(commandsPackageName);
        List<Class<ICommand>> commands = reflection.findSubtypesOf(ICommand.class);
        COMMAND_REGISTRY = new Registry(commands);
    }

    public static void setBotPrefix(@Nonnull String prefix) {
        Command.BOT_PREFIX = prefix;
    }

    public static void setOwners(@Nonnull Long[] owners) {
        Command.OWNERS.clear();
        Collections.addAll(Command.OWNERS, owners);
    }

    @EventSubscriber
    public void handle(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        if (message.isEmpty()) {
            return;
        }

        if (!message.startsWith(Command.BOT_PREFIX)) {
            return;
        }

        if (event.getGuild() != null) {
            long userID = event.getAuthor().getLongID();
            long guildID = event.getGuild().getLongID();
            Member member = new Member(userID, guildID);
            member.createIfNotExists();
            member = member.get().get(0);
            if (member.isBlacklisted) {
                return;
            }
        }

        COMMAND_REGISTRY.resolveCommand(event);
    }

}

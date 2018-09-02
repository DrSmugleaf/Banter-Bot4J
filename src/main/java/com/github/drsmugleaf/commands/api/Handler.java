package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import com.github.drsmugleaf.commands.api.registry.Registry;
import com.github.drsmugleaf.database.models.Member;
import com.github.drsmugleaf.reflection.Reflection;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

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
        List<Class<Command>> commands = reflection.findSubtypesOf(Command.class);
        COMMAND_REGISTRY = new Registry(commands);
    }

    @EventSubscriber
    public void handle(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        if (message.isEmpty()) {
            return;
        }

        if (!message.startsWith(BanterBot4J.BOT_PREFIX)) {
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

        CommandSearchResult command = COMMAND_REGISTRY.findCommand(event);
        if (command == null) {
            return;
        }

        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);
        CommandInfo annotation = command.COMMAND.getDeclaredAnnotation(CommandInfo.class);
        if (annotation != null) {
            if (commandEvent.getGuild() != null) {
                if (commandEvent.getGuild() != null) {
                    List<Permissions> annotationPermissions = Arrays.asList(annotation.permissions());
                    EnumSet<Permissions> authorPermissions = commandEvent.getAuthor().getPermissionsForGuild(commandEvent.getGuild());

                    if (!annotationPermissions.isEmpty() && Collections.disjoint(authorPermissions, Arrays.asList(annotation.permissions()))) {
                        commandEvent.reply("You don't have permission to use that command.");
                        return;
                    }
                }

                for (Tag tags : annotation.tags()) {
                    if (!tags.isValid(commandEvent)) {
                        commandEvent.reply(tags.message());
                        return;
                    }
                }
            }
        }

        Command.run(command, commandEvent);
    }

}

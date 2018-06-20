package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.database.models.Member;
import com.github.drsmugleaf.reflection.Reflection;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Handler {

    @Nonnull
    private static final Map<String, Class<ICommand>> COMMANDS = new HashMap<>();

    public static void loadCommands(@Nonnull String packageName) {
        Map<String, Class<ICommand>> commands = new HashMap<>();
        Reflection reflection = new Reflection(packageName);

        for (Class<ICommand> command : reflection.findSubtypesOf(ICommand.class)) {
            CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null && !annotation.name().isEmpty()) {
                commands.put(annotation.name().toLowerCase(), command);
            } else {
                commands.put(command.getSimpleName().toLowerCase(), command);
            }
        }

        COMMANDS.putAll(commands);
    }

    public static void setBotPrefix(@Nonnull String prefix) {
        Command.BOT_PREFIX = prefix;
    }

    public static void setOwners(@Nonnull Long[] owners) {
        Command.OWNERS.clear();
        Collections.addAll(Command.OWNERS, owners);
    }

    @EventSubscriber
    public static void handle(@Nonnull MessageReceivedEvent event) {
        String[] argsArray = event.getMessage().getContent().split(" ");
        if (argsArray.length == 0) {
            return;
        }

        if (!argsArray[0].startsWith(Command.BOT_PREFIX)) {
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

        String commandString = argsArray[0].substring(Command.BOT_PREFIX.length()).toLowerCase();
        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);
        if (COMMANDS.containsKey(commandString)) {
            Class<ICommand> commandClass = COMMANDS.get(commandString);
            Command.run(commandClass, commandEvent);
        }
    }

}

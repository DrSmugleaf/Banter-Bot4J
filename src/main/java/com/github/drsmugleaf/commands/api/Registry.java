package com.github.drsmugleaf.commands.api;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
class Registry {

    @Nonnull
    private final List<Class<ICommand>> COMMANDS;

    Registry(@Nonnull List<Class<ICommand>> commands) {
        COMMANDS = Collections.unmodifiableList(commands);
    }

    @Nullable
    Class<ICommand> resolveCommand(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContent().substring(Command.BOT_PREFIX.length()).toLowerCase();

        List<Class<ICommand>> matches = new ArrayList<>();
        for (Class<ICommand> command : COMMANDS) {
            CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);
            if (annotation == null || annotation.name().isEmpty()) {
                String commandName = command.getSimpleName().toLowerCase();
                if (message.equalsIgnoreCase(commandName)) {
                    return command;
                } else if (message.contains(commandName)) {
                    matches.add(command);
                }

                continue;
            }

            String commandName = annotation.name().toLowerCase();
            if (!commandName.isEmpty()) {
                if (message.equalsIgnoreCase(commandName)) {
                    return command;
                } else if (message.contains(commandName)) {
                    matches.add(command);
                }
            }

            for (String alias : annotation.aliases()) {
                alias = alias.toLowerCase();

                if (message.equalsIgnoreCase(alias)) {
                    return command;
                } else if (message.contains(alias)) {
                    matches.add(command);
                }
            }
        }

        return matches.get(0);
    }

}

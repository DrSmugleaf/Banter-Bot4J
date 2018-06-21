package com.github.drsmugleaf.commands.api;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

        for (Class<ICommand> command : COMMANDS) {
            CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);
            if (annotation == null || annotation.name().isEmpty()) {
                String commandName = command.getSimpleName().toLowerCase();
                if (message.contains(commandName)) {
                    return command;
                } else {
                    continue;
                }
            }

            String commandName = annotation.name().toLowerCase();
            if (!commandName.isEmpty() && message.contains(commandName)) {
                return command;
            }

            for (String alias : annotation.aliases()) {
                alias = alias.toLowerCase();
                if (message.contains(alias)) {
                    return command;
                }
            }
        }

        return null;
    }

}

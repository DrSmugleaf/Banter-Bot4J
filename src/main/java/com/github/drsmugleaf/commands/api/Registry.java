package com.github.drsmugleaf.commands.api;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

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
    private AbstractMap.SimpleEntry<Class<ICommand>, String> findCommand(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContent().substring(Command.BOT_PREFIX.length()).toLowerCase();
        List<AbstractMap.SimpleEntry<Class<ICommand>, String>> matches = new ArrayList<>();

        for (Class<ICommand> command : COMMANDS) {
            CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);
            if (annotation == null || annotation.name().isEmpty()) {
                String commandName = command.getSimpleName().toLowerCase();
                if (message.equalsIgnoreCase(commandName)) {
                    return new AbstractMap.SimpleEntry<>(command, commandName);
                } else if (message.contains(commandName)) {
                    matches.add(new AbstractMap.SimpleEntry<>(command, commandName));
                }

                continue;
            }

            String commandName = annotation.name().toLowerCase();
            if (!commandName.isEmpty()) {
                if (message.equalsIgnoreCase(commandName)) {
                    return new AbstractMap.SimpleEntry<>(command, commandName);
                } else if (message.contains(commandName)) {
                    matches.add(new AbstractMap.SimpleEntry<>(command, commandName));
                }
            }

            for (String alias : annotation.aliases()) {
                alias = alias.toLowerCase();

                if (message.equalsIgnoreCase(alias)) {
                    return new AbstractMap.SimpleEntry<>(command, alias);
                } else if (message.contains(alias)) {
                    matches.add(new AbstractMap.SimpleEntry<>(command, alias));
                }
            }
        }

        return getBestMatch(message, matches);
    }

    @Nullable
    private AbstractMap.SimpleEntry<Class<ICommand>, String> getBestMatch(
            @Nonnull String message,
            @Nonnull List<AbstractMap.SimpleEntry<Class<ICommand>, String>> matches
    ) {
        if (matches.isEmpty()) {
            return null;
        }

        List<String> argsList = Arguments.parseArgs(message);
        while (argsList.size() > 0) {
            String args = String.join(" ", argsList);

            for (AbstractMap.SimpleEntry<Class<ICommand>, String> match : matches) {
                if (match.getValue().equals(args)) {
                    return match;
                }
            }

            argsList.remove(argsList.size() - 1);
        }

        Command.LOGGER.warn("More than 1 command match found for message " + message + ". Matches: " + matches);

        return matches.get(0);
    }

    void resolveCommand(@Nonnull MessageReceivedEvent event) {
        AbstractMap.SimpleEntry<Class<ICommand>, String> command = findCommand(event);
        if (command == null) {
            return;
        }

        String commandName = Command.BOT_PREFIX + command.getValue();
        String arguments = event.getMessage().getFormattedContent();
        arguments = arguments.replaceFirst(commandName, "").trim();
        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);
        Command.run(command.getKey(), commandEvent, arguments);
    }

}

package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
class Registry {

    @Nonnull
    private final List<Class<Command>> COMMANDS;

    Registry(@Nonnull List<Class<Command>> commands) {
        COMMANDS = Collections.unmodifiableList(commands);

        List<AbstractMap.SimpleEntry<Class<Command>, String>> duplicates = findDuplicates();
        if (!duplicates.isEmpty()) {
            String duplicatesString = formatDuplicates(duplicates);
            throw new DuplicateCommandException(duplicatesString);
        }
    }

    @Nonnull
    private List<AbstractMap.SimpleEntry<Class<Command>, String>> findDuplicates() {
        Set<String> uniqueAliases = new HashSet<>();
        List<AbstractMap.SimpleEntry<Class<Command>, String>> duplicateAliases = new ArrayList<>();

        for (Class<Command> command : COMMANDS) {
            String commandName = Command.getName(command);
            if (!uniqueAliases.add(commandName)) {
                duplicateAliases.add(new AbstractMap.SimpleEntry<>(command, commandName));
            }

            CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null) {
                for (String alias : annotation.aliases()) {
                    alias = alias.toLowerCase();

                    if (!uniqueAliases.add(alias)) {
                        duplicateAliases.add(new AbstractMap.SimpleEntry<>(command, alias));
                    }
                }
            }
        }

        return duplicateAliases;
    }

    @Nonnull
    private String formatDuplicates(@Nonnull List<AbstractMap.SimpleEntry<Class<Command>, String>> duplicates) {
        if (duplicates.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder("Duplicate command names found:");
        for (Map.Entry<Class<Command>, String> entry : duplicates) {
            builder
                    .append("\n")
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue());
        }

        return builder.toString();
    }

    @Nullable
    private AbstractMap.SimpleEntry<Class<Command>, String> findCommand(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContent().substring(Command.BOT_PREFIX.length()).toLowerCase();
        List<AbstractMap.SimpleEntry<Class<Command>, String>> matches = new ArrayList<>();

        for (Class<Command> command : COMMANDS) {
            String commandName = Command.getName(command);
            if (message.equalsIgnoreCase(commandName)) {
                return new AbstractMap.SimpleEntry<>(command, commandName);
            } else if (message.contains(commandName)) {
                matches.add(new AbstractMap.SimpleEntry<>(command, commandName));
            }

            List<String> aliases = Command.getAliases(command);
            CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null) {
                for (String alias : annotation.aliases()) {
                    alias = alias.toLowerCase();

                    if (message.equalsIgnoreCase(alias)) {
                        return new AbstractMap.SimpleEntry<>(command, alias);
                    } else if (message.contains(alias)) {
                        matches.add(new AbstractMap.SimpleEntry<>(command, alias));
                    }
                }
            }
        }

        return getBestMatch(message, matches);
    }

    @Nullable
    private AbstractMap.SimpleEntry<Class<Command>, String> getBestMatch(
            @Nonnull String message,
            @Nonnull List<AbstractMap.SimpleEntry<Class<Command>, String>> matches
    ) {
        if (matches.isEmpty()) {
            return null;
        }

        List<String> argsList = Arguments.parseArgs(message);
        while (argsList.size() > 0) {
            String args = String.join(" ", argsList);

            for (AbstractMap.SimpleEntry<Class<Command>, String> match : matches) {
                if (match.getValue().equals(args)) {
                    return match;
                }
            }

            argsList.remove(argsList.size() - 1);
        }

        BanterBot4J.warn("More than 1 command match found for message " + message + ". Matches: " + matches);

        return matches.get(0);
    }

    void resolveCommand(@Nonnull MessageReceivedEvent event) {
        AbstractMap.SimpleEntry<Class<Command>, String> command = findCommand(event);
        if (command == null) {
            return;
        }

        String commandName = Command.BOT_PREFIX + command.getValue();
        String arguments = event.getMessage().getFormattedContent();
        arguments = arguments.replaceFirst("(?i)" + commandName, "").trim();
        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);
        Command.run(command.getKey(), commandEvent, arguments);
    }

}

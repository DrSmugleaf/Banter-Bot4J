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

        List<CommandSearchResult> duplicates = findDuplicates();
        if (!duplicates.isEmpty()) {
            String duplicatesString = formatDuplicates(duplicates);
            throw new DuplicateCommandException(duplicatesString);
        }
    }

    @Nonnull
    private List<CommandSearchResult> findDuplicates() {
        Set<String> uniqueAliases = new HashSet<>();
        List<CommandSearchResult> duplicateAliases = new ArrayList<>();

        for (Class<Command> command : COMMANDS) {
            String commandName = Command.getName(command);
            if (!uniqueAliases.add(commandName)) {
                duplicateAliases.add(new CommandSearchResult(command, commandName));
            }

            CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null) {
                for (String alias : annotation.aliases()) {
                    alias = alias.toLowerCase();

                    if (!uniqueAliases.add(alias)) {
                        duplicateAliases.add(new CommandSearchResult(command, alias));
                    }
                }
            }
        }

        return duplicateAliases;
    }

    @Nonnull
    private String formatDuplicates(@Nonnull List<CommandSearchResult> duplicates) {
        if (duplicates.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder("Duplicate command names found:");
        for (CommandSearchResult duplicate : duplicates) {
            builder
                    .append("\n")
                    .append(duplicate.COMMAND)
                    .append(": ")
                    .append(duplicate.MATCHED_NAME);
        }

        return builder.toString();
    }

    @Nullable
    private CommandSearchResult findCommand(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContent().substring(BanterBot4J.BOT_PREFIX.length()).toLowerCase();
        List<CommandSearchResult> matches = new ArrayList<>();

        for (Class<Command> command : COMMANDS) {
            String commandName = Command.getName(command);
            if (message.equalsIgnoreCase(commandName)) {
                return new CommandSearchResult(command, commandName);
            } else if (message.contains(commandName)) {
                matches.add(new CommandSearchResult(command, commandName));
            }

            List<String> aliases = Command.getAliases(command);
            for (String alias : aliases) {
                alias = alias.toLowerCase();

                if (message.equalsIgnoreCase(alias)) {
                    return new CommandSearchResult(command, alias);
                } else if (message.contains(alias)) {
                    matches.add(new CommandSearchResult(command, alias));
                }
            }
        }

        return getBestMatch(message, matches);
    }

    @Nullable
    private CommandSearchResult getBestMatch(@Nonnull String message, @Nonnull List<CommandSearchResult> matches) {
        if (matches.isEmpty()) {
            return null;
        }

        List<String> argsList = Arguments.parseArgs(message);
        while (argsList.size() > 0) {
            String args = String.join(" ", argsList);

            for (CommandSearchResult match : matches) {
                if (match.MATCHED_NAME.equals(args)) {
                    return match;
                }
            }

            argsList.remove(argsList.size() - 1);
        }

        BanterBot4J.warn("No exact match found for " + message + ". Partial matches: " + matches);

        return matches.get(0);
    }

    void resolveCommand(@Nonnull MessageReceivedEvent event) {
        CommandSearchResult command = findCommand(event);
        if (command == null) {
            return;
        }

        String commandName = BanterBot4J.BOT_PREFIX + command.MATCHED_NAME;
        String arguments = event.getMessage().getFormattedContent();
        arguments = arguments.replaceFirst("(?i)" + commandName, "").trim();
        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);
        Command.run(command.COMMAND, commandEvent, arguments);
    }

}

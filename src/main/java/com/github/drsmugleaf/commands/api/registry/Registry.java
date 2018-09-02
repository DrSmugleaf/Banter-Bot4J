package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.*;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
public class Registry {

    @Nonnull
    private final List<Class<Command>> COMMANDS;

    public Registry(@Nonnull List<Class<Command>> commands) {
        COMMANDS = Collections.unmodifiableList(commands);

        List<CommandSearchResult> duplicates = findDuplicates();
        if (!duplicates.isEmpty()) {
            String duplicatesString = formatDuplicates(duplicates);
            throw new DuplicateCommandNameException(duplicatesString);
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
    public CommandSearchResult findCommand(@Nonnull MessageReceivedEvent event) {
        String message = event.getMessage().getContent().substring(BanterBot4J.BOT_PREFIX.length()).toLowerCase();
        List<CommandSearchResult> matches = new ArrayList<>();

        List<String> argsList = Arguments.parseArgs(message);
        while (argsList.size() > 0) {
            String args = String.join(" ", argsList);

            for (Class<Command> command : COMMANDS) {
                String commandName = Command.getName(command);
                if (commandName.equalsIgnoreCase(args)) {
                    matches.add(new CommandSearchResult(command, commandName));
                }

                List<String> aliases = Command.getAliases(command);
                for (String alias : aliases) {
                    if (alias.equalsIgnoreCase(args)) {
                        matches.add(new CommandSearchResult(command, alias));
                    }
                }
            }

            if (!matches.isEmpty()) {
                if (matches.size() > 1) {
                    BanterBot4J.warn("More than 1 match found for " + message + ". Matches: " + matches);
                }

                return matches.get(0);
            }

            argsList.remove(argsList.size() - 1);
        }

        return null;
    }

}

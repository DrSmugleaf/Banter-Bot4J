package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.converter.ConverterRegistry;
import com.google.common.collect.ImmutableList;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
public class CommandRegistry {

    private final ConverterRegistry CONVERTERS;
    private final ImmutableList<Entry<? extends Command>> ENTRIES;

    public CommandRegistry(List<Class<Command>> commands) {
        CONVERTERS = new ConverterRegistry();

        List<Entry<? extends Command>> entries = new ArrayList<>();
        for (Class<? extends Command> command : commands) {
            if (Modifier.isAbstract(command.getModifiers())) {
                continue;
            }

            Entry<? extends Command> entry;
            try {
                entry = command.getDeclaredConstructor().newInstance().toEntry();
            } catch (Exception e) {
                throw new IllegalArgumentException("Error creating instance of command " + command, e);
            }

            entries.add(entry);
        }

        ENTRIES = ImmutableList.copyOf(entries);
        CONVERTERS.registerConverters(entries);

        List<CommandSearchResult<?>> duplicates = findDuplicates();
        if (!duplicates.isEmpty()) {
            String duplicatesString = formatDuplicates(duplicates);
            throw new DuplicateCommandNameException(duplicatesString);
        }
    }

    public ConverterRegistry getConverters() {
        return CONVERTERS;
    }

    public ImmutableList<Entry<? extends Command>> getEntries() {
        return ENTRIES;
    }

    private List<CommandSearchResult<?>> findDuplicates() {
        Set<String> uniqueAliases = new HashSet<>();
        List<CommandSearchResult<?>> duplicateAliases = new ArrayList<>();

        for (Entry<? extends Command> entry : ENTRIES) {
            for (String alias : entry.getAllAliases()) {
                if (!uniqueAliases.add(alias)) {
                    CommandSearchResult<?> search = new CommandSearchResult<>(entry, alias);
                    duplicateAliases.add(search);
                }
            }
        }

        return duplicateAliases;
    }

    private String formatDuplicates(List<CommandSearchResult<?>> duplicates) {
        if (duplicates.isEmpty()) {
            return "No duplicate command names found";
        }

        StringBuilder builder = new StringBuilder("Duplicate command names found:");
        for (CommandSearchResult<?> duplicate : duplicates) {
            builder
                    .append("\n")
                    .append(duplicate.getEntry())
                    .append(": ")
                    .append(duplicate.getMatchedName());
        }

        return builder.toString();
    }

    @Nullable
    public CommandSearchResult<?> findCommand(String message) {
        if (message.startsWith(BanterBot4J.BOT_PREFIX)) {
            message = message.substring(BanterBot4J.BOT_PREFIX.length()).toLowerCase();
        }

        List<CommandSearchResult<?>> matches = new ArrayList<>();
        List<String> argsList = Arguments.parseArgs(message);
        while (argsList.size() > 0) {
            String args = String.join(" ", argsList);

            for (Entry<? extends Command> entry : ENTRIES) {
                for (String alias : entry.getAllAliases()) {
                    if (alias.equalsIgnoreCase(args)) {
                        CommandSearchResult<?> search = new CommandSearchResult<>(entry, alias);
                        matches.add(search);
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

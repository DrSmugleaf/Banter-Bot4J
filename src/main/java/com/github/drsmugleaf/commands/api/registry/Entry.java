package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/04/2019
 */
public class Entry {

    @Nonnull
    private final Class<? extends Command> COMMAND;

    @Nullable
    private final CommandInfo COMMAND_INFO;

    @Nonnull
    private final ImmutableList<CommandField> ARGUMENTS;

    protected Entry(@Nonnull Class<? extends Command> command) {
        COMMAND = command;
        COMMAND_INFO = command.getDeclaredAnnotation(CommandInfo.class);
        ARGUMENTS = ImmutableList.copyOf(CommandField.from(command));
    }

    @Nonnull
    public Class<? extends Command> getCommand() {
        return COMMAND;
    }

    @Nullable
    public CommandInfo getCommandInfo() {
        return COMMAND_INFO;
    }

    @Nonnull
    public String getName() {
        String commandName;
        CommandInfo annotation = getCommandInfo();

        if (annotation == null || annotation.name().isEmpty()) {
            commandName = getCommand().getSimpleName();
        } else {
            commandName = annotation.name();
        }

        return commandName.toLowerCase();
    }

    @Nonnull
    public List<String> getAliases() {
        CommandInfo annotation = getCommandInfo();

        List<String> commandAliases = new ArrayList<>();
        if (annotation == null || annotation.aliases().length == 0) {
            return commandAliases;
        }

        for (String alias : annotation.aliases()) {
            commandAliases.add(alias.toLowerCase());
        }

        return commandAliases;
    }

    @Nonnull
    public List<String> getAllAliases() {
        List<String> aliases = getAliases();
        aliases.add(getName());
        return aliases;
    }

    @Nonnull
    public ImmutableList<CommandField> getArguments() {
        return ARGUMENTS;
    }

}

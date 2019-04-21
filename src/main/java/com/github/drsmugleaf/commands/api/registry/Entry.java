package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
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
    private final ImmutableList<CommandField> COMMAND_FIELDS;

    protected Entry(@Nonnull Class<? extends Command> command) {
        COMMAND = command;
        COMMAND_INFO = command.getDeclaredAnnotation(CommandInfo.class);
        COMMAND_FIELDS = ImmutableList.copyOf(CommandField.from(command));
    }

    @Nonnull
    public static CommandReceivedEvent getEvent(@Nonnull Command command) {
        try {
            return (CommandReceivedEvent) Command.class.getField("EVENT").get(command);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error getting event field for command " + command, e);
        }
    }

    public static void setEventField(@Nonnull Command command, @Nonnull CommandReceivedEvent event) {
        try {
            Command.class.getDeclaredField("EVENT").set(command, event);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error setting event field for command " + command, e);
        }
    }

    @Nonnull
    public static Arguments getArgs(@Nonnull Command command) {
        try {
            return (Arguments) Command.class.getDeclaredField("ARGUMENTS").get(command);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error getting args field for command " + command, e);
        }
    }

    public static void setArgsField(@Nonnull Command command, @Nonnull Arguments args) {
        try {
            Command.class.getDeclaredField("ARGUMENTS").set(command, args);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error setting args field for command " + command, e);
        }
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
    public ImmutableList<CommandField> getCommandFields() {
        return COMMAND_FIELDS;
    }

    @Nonnull
    public Command newInstance() {
        try {
            return COMMAND.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Error creating instance of command " + getName(), e);
        }
    }

}

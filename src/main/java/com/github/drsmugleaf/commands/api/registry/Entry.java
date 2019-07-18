package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/04/2019
 */
public class Entry {

    private final Class<? extends Command> COMMAND;
    private final CommandInfo COMMAND_INFO;
    private final ImmutableList<CommandField> COMMAND_FIELDS;

    protected Entry(Class<? extends Command> command) {
        COMMAND = command;
        COMMAND_INFO = command.getDeclaredAnnotation(CommandInfo.class);
        COMMAND_FIELDS = ImmutableList.copyOf(CommandField.from(command));
    }

    public static void setEvent(Command command, CommandReceivedEvent event) {
        try {
            Command.class.getDeclaredField("EVENT").set(command, event);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error setting event field for command " + command, e);
        }
    }

    public static void setArgs(Command command, Arguments args) {
        try {
            Command.class.getDeclaredField("ARGUMENTS").set(command, args);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error setting args field for command " + command, e);
        }
    }

    public static void setSelfUser(Command command, CommandReceivedEvent event) {
        User selfUser = event
                .getClient()
                .getSelf()
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Unable to get self user"));

        try {
            Command.class.getDeclaredField("SELF_USER").set(command, selfUser);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error setting self user field for command " + command, e);
        }
    }

    public static void setSelfMember(Command command, CommandReceivedEvent event) {
        Member selfMember = event
                .getClient()
                .getSelf()
                .zipWith(Mono.justOrEmpty(event.getGuildId()))
                .flatMap(tuple -> tuple.getT1().asMember(tuple.getT2()))
                .blockOptional()
                .orElse(null);

        try {
            Command.class.getDeclaredField("SELF_MEMBER").set(command, selfMember);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error setting self member field for command " + command, e);
        }
    }

    public Class<? extends Command> getCommand() {
        return COMMAND;
    }

    public CommandInfo getCommandInfo() {
        return COMMAND_INFO;
    }

    public String getName() {
        String commandName;
        CommandInfo annotation = getCommandInfo();

        if (annotation.name().isEmpty()) {
            commandName = getCommand().getSimpleName();
        } else {
            commandName = annotation.name();
        }

        return commandName.toLowerCase();
    }

    public List<String> getAliases() {
        CommandInfo annotation = getCommandInfo();

        List<String> commandAliases = new ArrayList<>();
        if (annotation.aliases().length == 0) {
            return commandAliases;
        }

        for (String alias : annotation.aliases()) {
            commandAliases.add(alias.toLowerCase());
        }

        return commandAliases;
    }

    public List<String> getAllAliases() {
        List<String> aliases = getAliases();
        aliases.add(getName());
        return aliases;
    }

    public ImmutableList<CommandField> getCommandFields() {
        return COMMAND_FIELDS;
    }

    public Command newInstance() {
        try {
            return COMMAND.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Error creating instance of command " + getName(), e);
        }
    }

    public String getFormats() {
        StringBuilder formats = new StringBuilder();
        formats
                .append("**Formats:**\n")
                .append(BanterBot4J.BOT_PREFIX)
                .append(getName());

        for (CommandField field : getCommandFields()) {
            String fieldName = field.getField().getName();
            formats
                    .append(" ")
                    .append(fieldName);
        }

        return formats.toString();
    }

    public String getExamples() {
        StringBuilder examples = new StringBuilder();
        examples
                .append(BanterBot4J.BOT_PREFIX)
                .append(getName());

        for (CommandField field : getCommandFields()) {
            String example = field.getArgument().example();
            examples.append(" ");

            if (example.contains(" ")) {
                examples
                        .append("\"")
                        .append(example)
                        .append("\"");
            } else {
                examples.append(example);
            }
        }

        return examples.toString();
    }

}

package com.github.drsmugleaf.commands.api.registry.entry;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.ICommand;
import com.github.drsmugleaf.commands.api.arguments.Arguments;
import com.github.drsmugleaf.commands.api.converter.result.Result;
import com.github.drsmugleaf.commands.api.converter.transformer.TransformerSet;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.tags.Tag;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.google.common.collect.ImmutableList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 28/02/2020
 */
public abstract class Entry<T extends ICommand> {

    private final Class<T> COMMAND;
    private final CommandInfo COMMAND_INFO;
    private final ImmutableList<CommandField> COMMAND_FIELDS;
    private final TransformerSet TRANSFORMERS;

    public Entry(Class<T> command) {
        COMMAND = command;
        COMMAND_INFO = command.getDeclaredAnnotation(CommandInfo.class);
        COMMAND_FIELDS = ImmutableList.copyOf(CommandField.from(command));
        TRANSFORMERS = emptyInstance().getTransformers();
    }

    public Class<T> getCommand() {
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

    public TransformerSet getTransformers() {
        return TRANSFORMERS;
    }

    private T emptyInstance() {
        try {
            return getCommand().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Error creating instance of command " + getName(), e);
        }
    }

    public Result<T> newInstance(CommandReceivedEvent event, Arguments arguments) {
        T command = emptyInstance();

        for (CommandField commandField : getCommandFields()) {
            Field field = commandField.getField();
            field.setAccessible(true);

            Object def;
            try {
                def = field.get(command);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error getting command argument " + field, e);
            }

            Result<?> result = arguments.getArg(commandField, def);
            if (result.getErrorResponse() != null) {
                return new Result<>(null, result.getErrorResponse());
            }

            try {
                field.set(command, result.getElement());
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error setting command argument " + field, e);
            }
        }

        return new Result<>(command, null);
    }

    public boolean hasOptionalArguments() {
        return getCommandFields().stream().anyMatch(field -> field.getArgument().optional());
    }

    public String getFormats() {
        StringBuilder formats = new StringBuilder();
        formats
                .append("**Formats:**\n")
                .append(BanterBot4J.BOT_PREFIX)
                .append(getName());

        for (CommandField field : getCommandFields()) {
            formats.append(" ");
            String fieldName = field.getField().getName();
            if (field.getArgument().optional()) {
                formats
                        .append("[")
                        .append(fieldName)
                        .append("]");
            } else {
                formats.append(fieldName);
            }
        }

        return formats.toString();
    }

    public String getExamples() {
        List<StringBuilder> mandatory = new ArrayList<>();
        List<StringBuilder> optional = new ArrayList<>();

        for (CommandField field : getCommandFields()) {
            List<StringBuilder> examples = Arrays
                    .stream(field.getArgument().examples())
                    .map(example -> example.contains(" ") ? "\"" + example + "\"" : example)
                    .map(StringBuilder::new)
                    .collect(Collectors.toList());

            boolean isOptional = field.getArgument().optional();
            if (mandatory.isEmpty() || optional.isEmpty()) {
                Stream<StringBuilder> stream = examples
                        .stream()
                        .map(example -> example.insert(0, " ").insert(0, getName()).insert(0, BanterBot4J.BOT_PREFIX));

                stream.forEachOrdered(optional::add);
                if (!isOptional) {
                    for (StringBuilder example : optional) {
                        mandatory.add(new StringBuilder(example));
                    }
                }
            } else {
                for (StringBuilder example : examples) {
                    if (isOptional) {
                        for (StringBuilder optionalExample : optional) {
                            optionalExample.append(" ").append(example);
                        }
                    } else {
                        for (StringBuilder mandatoryExample : mandatory) {
                            mandatoryExample.append(" ").append(example);
                        }
                    }
                }
            }
        }

        StringBuilder examples = new StringBuilder("**Examples:**\n").append(String.join("\n", mandatory));
        if (hasOptionalArguments()) {
            examples.append("\n").append(String.join("\n", optional));
        }

        return examples.toString();
    }

    public String getFormatsExamples() {
        return getFormats() + "\n" + getExamples();
    }

    public void executeTags(CommandReceivedEvent event) {
        Tags[] tags = getCommandInfo().tags();
        for (Tag tag : tags) {
            tag.execute(event);
        }
    }

}

package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.converter.ConversionException;
import com.github.drsmugleaf.commands.api.converter.Converter;
import com.github.drsmugleaf.commands.api.converter.Result;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import com.github.drsmugleaf.commands.api.registry.Entry;
import discord4j.core.event.domain.message.MessageCreateEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 30/05/2018.
 */
public class Arguments extends ArrayList<String> {

    private static final Pattern SPLIT_ON_SPACES_EXCEPT_WITHIN_QUOTES = Pattern.compile("\"([^\"]*)\"|'([^']*)'|[^\\s]+");
    private final CommandSearchResult RESULT;
    private final CommandReceivedEvent EVENT;

    Arguments(CommandSearchResult result, CommandReceivedEvent event) {
        super(getArgs(result, event));
        RESULT = result;
        EVENT = event;
    }

    private static String getArg(Matcher matcher) {
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String match = matcher.group(i);
            if (match != null) {
                return match;
            }
        }

        return matcher.group();
    }

    private static String extractArgs(CommandSearchResult command, MessageCreateEvent event) {
        String matchedCommandName = command.getMatchedName();
        return event
                .getMessage()
                .getContent()
                .flatMap(content -> {
                    int index = content.toLowerCase().indexOf(matchedCommandName.toLowerCase());
                    content = content.substring(index + matchedCommandName.length()).trim();
                    return Optional.of(content);
                }).get();
    }

    public static List<String> parseArgs(String argumentsString) {
        List<String> args = new ArrayList<>();
        Matcher matcher = SPLIT_ON_SPACES_EXCEPT_WITHIN_QUOTES.matcher(argumentsString);

        while (matcher.find()) {
            String arg = getArg(matcher);
            args.add(arg);
        }

        return args;
    }

    public static List<String> getArgs(CommandSearchResult result, MessageCreateEvent event) {
        String argumentsString = extractArgs(result, event);
        return parseArgs(argumentsString);
    }

    public String first() {
        return get(0);
    }

    public String last() {
        return get(size() - 1);
    }

    public String getFrom(int start, int end) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(get(i)).append(" ");
        }

        return builder.toString().trim();
    }

    public String getFrom(int start) {
        return getFrom(start, size());
    }

    public boolean isDouble(int... indexes) {
        for (int i : indexes) {
            String argument = get(i).replace(',', '.');

            try {
                Double.parseDouble(argument);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    @Nullable
    public Integer findFirstIntegerIndex() {
        for (int i = 0; i < size(); i++) {
            if (isInteger(i)) {
                return i;
            }
        }

        return null;
    }

    public boolean hasInteger() {
        return findFirstIntegerIndex() != null;
    }

    public boolean isInteger(int... indexes) {
        for (int i : indexes) {
            String argument = get(i);

            try {
                Integer.parseInt(argument);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    public Double getDouble(int index) {
        String argument = get(index).replace(',', '.');
        return Double.parseDouble(argument);
    }

    @Nullable
    public Integer getInteger(int index) {
        String argument = get(index);
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.join(" ", this);
    }

    public boolean has(int index) {
        return index < size();
    }

    @SuppressWarnings("unchecked")
    public <E> Result<E> getArg(CommandField commandField, @Nullable E def) {
        Field field = commandField.getField();
        Argument argument = commandField.getArgument();
        int position = argument.position() - 1;

        if (!has(position)) {
            if (def != null) {
                return new Result<>(def, "");
            }

            if (!argument.optional()) {
                return new Result<>(null, "Invalid arguments.\n" + getInvalidArgumentsResponse());
            }

            return new Result<>(null, "");
        }

        Class<?> fieldType = field.getType();
        Converter<String, CommandReceivedEvent, ?> converter = BanterBot4J
                .getHandler()
                .getRegistry()
                .getConverters()
                .find(String.class, CommandReceivedEvent.class, fieldType);
        if (converter == null) {
            throw new IllegalStateException("No converter found for type" + fieldType);
        }

        Object arg;
        String stringArg = getStringArg(commandField);
        Result<?> result;
        try {
            result = converter.convert(commandField, stringArg, EVENT);
        } catch (ConversionException e) {
            return new Result<>(null, "Invalid " + field.getName() + ".\n" + getInvalidArgumentsResponse());
        }

        if (!result.isValid()) {
            return new Result<>(null, result.getErrorResponse());
        }

        arg = result.getElement();

        if (arg == null) {
            return new Result<>(null, "Invalid arguments.\n" + getInvalidArgumentsResponse());
        } else {
            return new Result<>((E) arg, "");
        }
    }

    @Nullable
    public String getStringArg(CommandField field) {
        int position = field.getArgument().position() - 1;
        long words = field.getArgument().maxWords();

        if (field.getField().getType() == String.class) {
            List<String> strings = new ArrayList<>();
            for (int i = position; i < position + words && i < size(); i++) {
                strings.add(get(i));
            }

            return String.join(" ", strings);
        }

        return get(position);
    }

    public String getInvalidArgumentsResponse() {
        Entry entry = RESULT.getEntry();
        return entry.getFormats() + "\n**Example:**\n" + entry.getExamples();
    }

}

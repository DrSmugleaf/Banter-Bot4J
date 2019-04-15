package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import com.github.drsmugleaf.commands.api.registry.Entry;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 30/05/2018.
 */
public class Arguments extends ArrayList<String> {

    @Nonnull
    private static final Pattern SPLIT_ON_SPACES_EXCEPT_WITHIN_QUOTES = Pattern.compile("\"([^\"]*)\"|'([^']*)'|[^\\s]+");

    @Nonnull
    private final CommandSearchResult RESULT;

    @Nonnull
    private final CommandReceivedEvent EVENT;

    Arguments(@Nonnull CommandSearchResult result, @Nonnull CommandReceivedEvent event) {
        super(getArgs(result, event));
        RESULT = result;
        EVENT = event;
    }

    @Nonnull
    private static String getArg(@Nonnull Matcher matcher) {
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String match = matcher.group(i);
            if (match != null) {
                return match;
            }
        }

        return matcher.group();
    }

    @Nonnull
    private static String extractArgs(@Nonnull CommandSearchResult command, @Nonnull MessageReceivedEvent event) {
        String matchedCommandName = command.getMatchedName();
        String argumentsString = event.getMessage().getFormattedContent();
        int index = argumentsString.toLowerCase().indexOf(matchedCommandName.toLowerCase());
        argumentsString = argumentsString.substring(index + matchedCommandName.length()).trim();
        return argumentsString;
    }

    @Nonnull
    public static List<String> parseArgs(@Nonnull String argumentsString) {
        List<String> args = new ArrayList<>();
        Matcher matcher = SPLIT_ON_SPACES_EXCEPT_WITHIN_QUOTES.matcher(argumentsString);

        while (matcher.find()) {
            String arg = getArg(matcher);
            args.add(arg);
        }

        return args;
    }

    @Nonnull
    public static List<String> getArgs(@Nonnull CommandSearchResult result, @Nonnull MessageReceivedEvent event) {
        String argumentsString = extractArgs(result, event);
        return parseArgs(argumentsString);
    }

    @Nonnull
    public String first() {
        return get(0);
    }

    @Nonnull
    public String last() {
        return get(size() - 1);
    }

    @Nonnull
    public String getFrom(int start, int end) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(get(i)).append(" ");
        }

        return builder.toString().trim();
    }

    @Nonnull
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

    @Nonnull
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

    @Nullable
    public Object getArg(@Nonnull CommandField commandField) {
        Field field = commandField.getField();

        Argument argument = commandField.getArgument();
        int position = argument.position() - 1;
        if (!has(position)) {
            EVENT.reply(getInvalidArgumentsResponse());
            return null;
        }

        Object arg = null;
        Class<?> fieldType = field.getType();
        if (fieldType == String.class) {
            arg = get(position);
        } else if (fieldType == Integer.class) {
            arg = getInteger(position);

            int minimum = argument.minimum();
            int maximum = argument.maximum();
            if (arg != null) {
                if ((Integer) arg < minimum) {
                    EVENT.reply("Not enough " + field.getName() + " requested. Minimum: " + minimum);
                    return null;
                } else if ((Integer) arg > maximum) {
                    EVENT.reply("Too many " + field.getName() + " requested. Maximum: " + maximum);
                    return null;
                }
            }
        }

        if (arg == null) {
            EVENT.reply(getInvalidArgumentsResponse());
            return null;
        } else {
            return arg;
        }
    }

    @Nonnull
    public String getInvalidArgumentsResponse() {
        Entry entry = RESULT.getEntry();
        StringBuilder response = new StringBuilder();
        response
                .append("Invalid arguments.\n**Formats:**\n")
                .append(BanterBot4J.BOT_PREFIX)
                .append(entry.getName());

        for (CommandField field : entry.getCommandFields()) {
            String fieldName = field.getField().getName();
            response
                    .append(" ")
                    .append(fieldName);
        }

        response
                .append("\n**Example:**\n")
                .append(BanterBot4J.BOT_PREFIX)
                .append(entry.getName());

        for (CommandField field : entry.getCommandFields()) {
            String example = field.getArgument().example();
            response.append(" ");

            if (example.contains(" ")) {
                response
                        .append("\"")
                        .append(example)
                        .append("\"");
            } else {
                response.append(example);
            }
        }

        return response.toString();
    }

}

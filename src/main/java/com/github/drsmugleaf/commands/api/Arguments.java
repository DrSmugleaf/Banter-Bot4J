package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    Arguments(@Nonnull CommandSearchResult command, @Nonnull CommandReceivedEvent event) {
        super(getArgs(command, event));
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
        String matchedCommandName = command.MATCHED_NAME;
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
    public static List<String> getArgs(@Nonnull CommandSearchResult command, @Nonnull MessageReceivedEvent event) {
        String argumentsString = extractArgs(command, event);
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

    public double getDouble(int index) {
        String argument = get(index).replace(',', '.');
        return Double.parseDouble(argument);
    }

    public int getInteger(int index) {
        String argument = get(index);
        return Integer.parseInt(argument);
    }

    @Override
    public String toString() {
        return String.join(" ", this);
    }

}

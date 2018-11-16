package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 30/05/2018.
 */
public class Arguments extends ArrayList<String> {

    @NotNull
    private static final Pattern SPLIT_ON_SPACES_EXCEPT_WITHIN_QUOTES = Pattern.compile("\"([^\"]*)\"|'([^']*)'|[^\\s]+|(\n)");

    Arguments(@NotNull CommandSearchResult command, @NotNull CommandReceivedEvent event) {
        super(getArgs(command, event));
    }

    @NotNull
    private static String getArg(@NotNull Matcher matcher) {
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String match = matcher.group(i);
            if (match != null) {
                return match;
            }
        }

        return matcher.group();
    }

    @NotNull
    private static String extractArgs(@NotNull CommandSearchResult command, @NotNull MessageReceivedEvent event) {
        String matchedCommandName = command.MATCHED_NAME;
        String argumentsString = event.getMessage().getFormattedContent();
        int index = argumentsString.toLowerCase().indexOf(matchedCommandName.toLowerCase());
        argumentsString = argumentsString.substring(index + matchedCommandName.length()).trim();
        return argumentsString;
    }

    @NotNull
    public static List<String> parseArgs(@NotNull String argumentsString) {
        List<String> args = new ArrayList<>();
        Matcher matcher = SPLIT_ON_SPACES_EXCEPT_WITHIN_QUOTES.matcher(argumentsString);

        while (matcher.find()) {
            String arg = getArg(matcher);
            args.add(arg);
        }

        return args;
    }

    @NotNull
    public static List<String> getArgs(@NotNull CommandSearchResult command, @NotNull MessageReceivedEvent event) {
        String argumentsString = extractArgs(command, event);
        return parseArgs(argumentsString);
    }

    @NotNull
    public String first() {
        return get(0);
    }

    @NotNull
    public String last() {
        return get(size() - 1);
    }

    @NotNull
    public String getFrom(int start, int end) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(get(i)).append(" ");
        }

        return builder.toString().trim();
    }

    @NotNull
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

package com.github.drsmugleaf.commands.api;

import javax.annotation.Nonnull;
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

    Arguments(@Nonnull String text) {
        super(parseArgs(text));
    }

    @Nonnull
    private static List<String> parseArgs(@Nonnull String text) {
        List<String> args = new ArrayList<>();
        Matcher matcher = SPLIT_ON_SPACES_EXCEPT_WITHIN_QUOTES.matcher(text);
        while (matcher.find()) {
            String arg = getArg(matcher);
            args.add(arg);
        }

        args.remove(0);

        return args;
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
    public String first() {
        return get(0);
    }

    @Nonnull
    public String last() {
        return get(size() - 1);
    }

}

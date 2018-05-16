package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.tripwire.API;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 15/05/2018.
 */
public class Tripwire extends AbstractCommand {

    @Nonnull
    private static List<String> getQuotedArguments(List<String> args) {
        List<String> groupedArguments = new ArrayList<>();
        String joinedArgs = String.join(" ", args);
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(joinedArgs);

        while (matcher.find()) {
            groupedArguments.add(matcher.group(1));
        }

        return groupedArguments;
    }

    @Nonnull
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"username\" \"password\"\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"DrSmugleaf Aulmais\" \"pAsSwOrD\"\n";
    }

    @CommandInfo
    public static void tripwireRoute(MessageReceivedEvent event, List<String> args) {
        args = getQuotedArguments(args);
        if (args.size() != 2) {
            sendMessage(event.getChannel(), wrongFormatResponse());
            return;
        }
    }

}

package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.tripwire.StarSystem;
import com.github.drsmugleaf.tripwire.SystemGraph;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"username\" \"password\" \"system1\" \"system2\"\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"DrSmugleaf Aulmais\" \"pAsSwOrD\" \"O-VWPB\" \"Jita\"\n";
    }

    @CommandInfo
    public static void tripwireRoute(MessageReceivedEvent event, List<String> args) {
        args = getQuotedArguments(args);
        if (args.size() != 4) {
            sendMessage(event.getChannel(), wrongFormatResponse());
            return;
        }

        Long id = event.getAuthor().getLongID();
        String username = args.get(0);
        String password = args.get(1);
        String from = args.get(2);
        String to = args.get(3);
        List<StarSystem> route = SystemGraph.getRoute(id, username, password, from, to);

        if (route == null) {
            sendMessage(event.getChannel(), "No route found from system " + from  + " to system " + to);
            return;
        }

        List<String> systemNames = route.stream().map(system -> system.NAME).collect(Collectors.toList());
        String response = route.size() + " jumps: ";
        response += String.join(", ", systemNames);
        sendMessage(event.getChannel(), response);
    }

}

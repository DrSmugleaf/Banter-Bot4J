package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.AbstractCommand;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.eve.Systems;
import com.github.drsmugleaf.tripwire.route.Route;
import com.github.drsmugleaf.tripwire.route.StarSystem;
import com.github.drsmugleaf.tripwire.route.SystemGraph;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 15/05/2018.
 */
public class Tripwire extends AbstractCommand {

    @Nonnull
    private static final Map<IUser, Route> ROUTES = new HashMap<>();

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
    private static List<String> removeQuotes(@Nonnull List<String> args) {
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);

            if (arg.contains("\"")) {
                arg = arg.replaceAll("\"", "");
                args.set(i, arg);
            }
        }

        return args;
    }

    @Nonnull
    private static String routeWrongFormatResponse() {
        return "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"username\" \"password\" \"system1\" \"system2\"\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"DrSmugleaf Aulmais\" \"pAsSwOrD\" \"O-VWPB\" \"Jita\"\n";
    }

    @Nonnull
    private static String bridgeWrongFormatResponse() {
        return "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireBridge system1 system2\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute O-VWPB Jita";
    }

    @CommandInfo
    public static void tripwireRoute(MessageReceivedEvent event, List<String> args) {
        args = getQuotedArguments(args);
        if (args.size() != 4) {
            sendMessage(event.getChannel(), routeWrongFormatResponse());
            return;
        }

        Long id = event.getAuthor().getLongID();
        String username = args.get(0);
        String password = args.get(1);
        String from = args.get(2);
        String to = args.get(3);
        Route route = SystemGraph.getRoute(id, username, password, from, to);

        if (route == null) {
            sendMessage(event.getChannel(), "No route found from system " + from  + " to system " + to);
            return;
        }

        ROUTES.put(event.getAuthor(), route);
        sendMessage(event.getChannel(), route.info());
    }

    @CommandInfo
    public static void tripwireBridge(MessageReceivedEvent event, List<String> args) {
        IUser author = event.getAuthor();
        if (!ROUTES.containsKey(author)) {
            sendMessage(event.getChannel(), "Create a route first with " + BanterBot4J.BOT_PREFIX + "tripwireRoute");
            return;
        }

        if (args.size() != 2) {
            sendMessage(event.getChannel(), bridgeWrongFormatResponse());
            return;
        }

        args = removeQuotes(args);

        List<String> invalidSystems = new ArrayList<>();
        for (String system : args) {
            if (!Systems.NAMES.containsValue(system)) {
                invalidSystems.add(system);
            }
        }

        if (!invalidSystems.isEmpty()) {
            String response = "Invalid system names: " + String.join(", ", invalidSystems);
            sendMessage(event.getChannel(), response);
            return;
        }

        Route route = ROUTES.get(author);
        StarSystem firstSystem = null;
        StarSystem secondSystem = null;
        for (StarSystem node : route.GRAPH.NODES) {
            if (node.NAME.equalsIgnoreCase(args.get(0))) {
                firstSystem = node;
            }

            if (node.NAME.equalsIgnoreCase(args.get(1))) {
                secondSystem = node;
            }
        }

        if (firstSystem == null) {
            sendMessage(event.getChannel(), args.get(0) + " isn't in the route");
            return;
        }

        if (secondSystem == null) {
            sendMessage(event.getChannel(), args.get(1) + " isn't in the route");
            return;
        }

        firstSystem.addDestination(secondSystem, 0);
        route.recalculate();

        sendMessage(event.getChannel(), route.info());
    }

}

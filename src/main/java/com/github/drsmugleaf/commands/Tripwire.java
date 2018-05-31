package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.eve.Systems;
import com.github.drsmugleaf.tripwire.route.Route;
import com.github.drsmugleaf.tripwire.route.StarSystem;
import com.github.drsmugleaf.tripwire.route.SystemGraph;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 15/05/2018.
 */
public class Tripwire {

    @Nonnull
    private static final Map<IUser, Route> ROUTES = new HashMap<>();

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
    public static void tripwireRoute(CommandReceivedEvent event) {
        if (event.ARGS.size() != 4) {
            event.reply(routeWrongFormatResponse());
            return;
        }

        Long id = event.getAuthor().getLongID();
        String username = event.ARGS.get(0);
        String password = event.ARGS.get(1);
        String from = event.ARGS.get(2);
        String to = event.ARGS.get(3);
        Route route = SystemGraph.getRoute(id, username, password, from, to);

        if (route == null) {
            event.reply("No route found from system " + from  + " to system " + to);
            return;
        }

        ROUTES.put(event.getAuthor(), route);
        event.reply(route.info());
    }

    @CommandInfo
    public static void tripwireBridge(CommandReceivedEvent event) {
        IUser author = event.getAuthor();
        if (!ROUTES.containsKey(author)) {
            event.reply("Create a route first with " + BanterBot4J.BOT_PREFIX + "tripwireRoute");
            return;
        }

        if (event.ARGS.size() != 2) {
            event.reply(bridgeWrongFormatResponse());
            return;
        }

        List<String> invalidSystems = new ArrayList<>();
        for (String system : event.ARGS) {
            if (!Systems.NAMES.containsValue(system)) {
                invalidSystems.add(system);
            }
        }

        if (!invalidSystems.isEmpty()) {
            String response = "Invalid system names: " + String.join(", ", invalidSystems);
            event.reply(response);
            return;
        }

        Route route = ROUTES.get(author);
        StarSystem firstSystem = null;
        StarSystem secondSystem = null;
        for (StarSystem node : route.GRAPH.NODES) {
            if (node.NAME.equalsIgnoreCase(event.ARGS.get(0))) {
                firstSystem = node;
            }

            if (node.NAME.equalsIgnoreCase(event.ARGS.get(1))) {
                secondSystem = node;
            }
        }

        if (firstSystem == null) {
            event.reply(event.ARGS.get(0) + " isn't in the route");
            return;
        }

        if (secondSystem == null) {
            event.reply(event.ARGS.get(1) + " isn't in the route");
            return;
        }

        firstSystem.addDestination(secondSystem, 0);
        route.recalculate();

        event.reply(route.info());
    }

}

package com.github.drsmugleaf.commands.tripwire;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.eve.Systems;
import com.github.drsmugleaf.tripwire.route.Route;
import com.github.drsmugleaf.tripwire.route.StarSystem;
import discord4j.core.object.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public class TripwireBridge extends Command {

    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireBridge system1 system2\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute O-VWPB Jita";
    }

    @Override
    public void run() {
        User author = EVENT
                .getMessage()
                .getAuthor()
                .orElseThrow(() -> new IllegalStateException("Couldn't get the message's author. Message: " + EVENT.getMessage()));

        if (!TripwireRoute.ROUTES.containsKey(author)) {
            reply("Create a route first with " + BanterBot4J.BOT_PREFIX + "tripwireRoute.").subscribe();
            return;
        }

        if (ARGUMENTS.size() != 2) {
            reply(invalidArgumentsResponse()).subscribe();
            return;
        }

        List<String> invalidSystems = new ArrayList<>();
        for (String system : ARGUMENTS) {
            if (!Systems.NAMES.containsValue(system)) {
                invalidSystems.add(system);
            }
        }

        if (!invalidSystems.isEmpty()) {
            String response = "Invalid system names: " + String.join(", ", invalidSystems + ".");
            reply(response).subscribe();
            return;
        }

        Route route = TripwireRoute.ROUTES.get(author);
        StarSystem firstSystem = null;
        StarSystem secondSystem = null;
        for (StarSystem node : route.GRAPH.NODES) {
            if (node.NAME.equalsIgnoreCase(ARGUMENTS.get(0))) {
                firstSystem = node;
            }

            if (node.NAME.equalsIgnoreCase(ARGUMENTS.get(1))) {
                secondSystem = node;
            }
        }

        if (firstSystem == null) {
            reply(ARGUMENTS.get(0) + " isn't in the route.").subscribe();
            return;
        }

        if (secondSystem == null) {
            reply(ARGUMENTS.get(1) + " isn't in the route.").subscribe();
            return;
        }

        firstSystem.addDestination(secondSystem, 0);
        route.recalculate();

        reply(route.info()).subscribe();
    }

}

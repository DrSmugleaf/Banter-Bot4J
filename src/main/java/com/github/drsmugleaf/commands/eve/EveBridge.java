package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.eve.EVE;
import com.github.drsmugleaf.eve.Route;
import com.github.drsmugleaf.eve.StarSystem;
import discord4j.core.object.entity.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(
        description = "Join two or more Eve Online systems together with a jump bridge for the route finder"
)
public class EveBridge extends Command {

    @Argument(position = 1, examples = "O-VWPB Jita G-ME2K", maxWords = Integer.MAX_VALUE)
    private String systems;

    @Override
    public void run() {
        User author = EVENT
                .getMessage()
                .getAuthor()
                .orElseThrow(() -> new IllegalStateException("Couldn't get the message's author. Message: " + EVENT.getMessage()));

        if (!EveRoute.ROUTES.containsKey(author)) {
            reply("Create a route first with " + BanterBot4J.BOT_PREFIX + "everoute.").subscribe();
            return;
        }

        Set<String> invalidSystems = new HashSet<>();
        Set<String> systemsNotInRoute = new HashSet<>();
        String[] systemNames = systems.split(" ");
        Route route = EveRoute.ROUTES.get(author);
        for (String systemName1 : systemNames) {
            if (!EVE.getSystems().containsValue(systemName1)) {
                invalidSystems.add(systemName1);
                continue;
            }

            StarSystem system1 = null;
            for (StarSystem node : route.GRAPH.NODES) {
                if (node.NAME.equalsIgnoreCase(systemName1)) {
                    system1 = node;
                    break;
                }
            }

            if (system1 == null) {
                systemsNotInRoute.add(systemName1);
                continue;
            }

            for (String systemName2 : systemNames) {
                if (!EVE.getSystems().containsValue(systemName2)) {
                    invalidSystems.add(systemName2);
                    continue;
                }

                StarSystem system2 = null;
                for (StarSystem node : route.GRAPH.NODES) {
                    if (node.NAME.equalsIgnoreCase(systemName2)) {
                        system2 = node;
                        break;
                    }
                }

                if (system2 == null) {
                    systemsNotInRoute.add(systemName2);
                    continue;
                }

                system1.addDestination(system2, 0);
            }

            route.recalculate();
        }

        StringBuilder response = new StringBuilder();
        if (!invalidSystems.isEmpty()) {
            response
                    .append("Invalid system names: ")
                    .append(String.join(", ", invalidSystems))
                    .append(".");
        }

        if (!systemsNotInRoute.isEmpty()) {
            response
                    .append("Systems not in route: ")
                    .append(String.join(", ", systemsNotInRoute))
                    .append(".");
        }

        if (response.length() == 0) {
            response.append(route.info());
        }

        reply(response.toString()).subscribe();
    }

}

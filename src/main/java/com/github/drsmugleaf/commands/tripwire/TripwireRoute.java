package com.github.drsmugleaf.commands.tripwire;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.tripwire.route.Route;
import com.github.drsmugleaf.tripwire.route.SystemGraph;
import discord4j.core.object.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public class TripwireRoute extends Command {

    static final Map<User, Route> ROUTES = new HashMap<>();

    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"username\" \"password\" \"system1\" \"system2\"\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"DrSmugleaf Aulmais\" \"pAsSwOrD\" \"O-VWPB\" \"Jita\"";
    }

    @Override
    public void run() {
        if (ARGUMENTS.size() != 4) {
            reply(invalidArgumentsResponse()).subscribe();
            return;
        }

        User author = EVENT
                .getMessage()
                .getAuthor()
                .orElseThrow(() -> new IllegalStateException("Couldn't get the message's author. Message: " + EVENT.getMessage()));
        String username = ARGUMENTS.get(0);
        String password = ARGUMENTS.get(1);
        String from = ARGUMENTS.get(2);
        String to = ARGUMENTS.get(3);
        Route route = SystemGraph.getRoute(author.getId().asLong(), username, password, from, to);

        if (route == null) {
            reply("No route found from system " + from  + " to system " + to + ".").subscribe();
            return;
        }

        ROUTES.put(author, route);
        reply(route.info()).subscribe();
    }

}

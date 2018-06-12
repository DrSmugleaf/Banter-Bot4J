package com.github.drsmugleaf.commands.tripwire;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.tripwire.route.Route;
import com.github.drsmugleaf.tripwire.route.SystemGraph;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public class TripwireRoute extends Command {

    @Nonnull
    static final Map<IUser, Route> ROUTES = new HashMap<>();

    @Nonnull
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               BOT_PREFIX + "tripwireRoute \"username\" \"password\" \"system1\" \"system2\"\n" +
               "**Examples:**\n" +
               BOT_PREFIX + "tripwireRoute \"DrSmugleaf Aulmais\" \"pAsSwOrD\" \"O-VWPB\" \"Jita\"";
    }

    @Override
    protected void run(@Nonnull CommandReceivedEvent event) {
        if (event.ARGS.size() != 4) {
            event.reply(wrongFormatResponse());
            return;
        }

        Long id = event.getAuthor().getLongID();
        String username = event.ARGS.get(0);
        String password = event.ARGS.get(1);
        String from = event.ARGS.get(2);
        String to = event.ARGS.get(3);
        Route route = SystemGraph.getRoute(id, username, password, from, to);

        if (route == null) {
            event.reply("No route found from system " + from  + " to system " + to + ".");
            return;
        }

        ROUTES.put(event.getAuthor(), route);
        event.reply(route.info());
    }

}

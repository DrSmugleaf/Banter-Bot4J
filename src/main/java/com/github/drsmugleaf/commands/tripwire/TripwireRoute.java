package com.github.drsmugleaf.commands.tripwire;

import com.github.drsmugleaf.commands.api.Arguments;
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

    protected TripwireRoute(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               botPrefix + "tripwireRoute \"username\" \"password\" \"system1\" \"system2\"\n" +
               "**Examples:**\n" +
               botPrefix + "tripwireRoute \"DrSmugleaf Aulmais\" \"pAsSwOrD\" \"O-VWPB\" \"Jita\"";
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        if (ARGS.size() != 4) {
            event.reply(wrongFormatResponse());
            return;
        }

        Long id = event.getAuthor().getLongID();
        String username = ARGS.get(0);
        String password = ARGS.get(1);
        String from = ARGS.get(2);
        String to = ARGS.get(3);
        Route route = SystemGraph.getRoute(id, username, password, from, to);

        if (route == null) {
            event.reply("No route found from system " + from  + " to system " + to + ".");
            return;
        }

        ROUTES.put(event.getAuthor(), route);
        event.reply(route.info());
    }

}

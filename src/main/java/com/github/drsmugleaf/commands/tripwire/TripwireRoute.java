package com.github.drsmugleaf.commands.tripwire;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.tripwire.route.Route;
import com.github.drsmugleaf.tripwire.route.SystemGraph;
import sx.blah.discord.handle.obj.IUser;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public class TripwireRoute extends Command {

    @NotNull
    static final Map<IUser, Route> ROUTES = new HashMap<>();

    protected TripwireRoute(@NotNull CommandReceivedEvent event, @NotNull Arguments args) {
        super(event, args);
    }

    @NotNull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"username\" \"password\" \"system1\" \"system2\"\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "tripwireRoute \"DrSmugleaf Aulmais\" \"pAsSwOrD\" \"O-VWPB\" \"Jita\"";
    }

    @Override
    public void run() {
        if (ARGS.size() != 4) {
            EVENT.reply(invalidArgumentsResponse());
            return;
        }

        Long id = EVENT.getAuthor().getLongID();
        String username = ARGS.get(0);
        String password = ARGS.get(1);
        String from = ARGS.get(2);
        String to = ARGS.get(3);
        Route route = SystemGraph.getRoute(id, username, password, from, to);

        if (route == null) {
            EVENT.reply("No route found from system " + from + " to system " + to + ".");
            return;
        }

        ROUTES.put(EVENT.getAuthor(), route);
        EVENT.reply(route.info());
    }

}

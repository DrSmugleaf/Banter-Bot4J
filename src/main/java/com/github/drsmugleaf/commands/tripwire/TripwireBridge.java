package com.github.drsmugleaf.commands.tripwire;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.eve.Systems;
import com.github.drsmugleaf.tripwire.route.Route;
import com.github.drsmugleaf.tripwire.route.StarSystem;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public class TripwireBridge extends Command {

    protected TripwireBridge(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               BOT_PREFIX + "tripwireBridge system1 system2\n" +
               "**Examples:**\n" +
               BOT_PREFIX + "tripwireRoute O-VWPB Jita";
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        IUser author = event.getAuthor();
        if (!TripwireRoute.ROUTES.containsKey(author)) {
            event.reply("Create a route first with " + BOT_PREFIX + "tripwireRoute.");
            return;
        }

        if (ARGS.size() != 2) {
            event.reply(wrongFormatResponse());
            return;
        }

        List<String> invalidSystems = new ArrayList<>();
        for (String system : ARGS) {
            if (!Systems.NAMES.containsValue(system)) {
                invalidSystems.add(system);
            }
        }

        if (!invalidSystems.isEmpty()) {
            String response = "Invalid system names: " + String.join(", ", invalidSystems + ".");
            event.reply(response);
            return;
        }

        Route route = TripwireRoute.ROUTES.get(author);
        StarSystem firstSystem = null;
        StarSystem secondSystem = null;
        for (StarSystem node : route.GRAPH.NODES) {
            if (node.NAME.equalsIgnoreCase(ARGS.get(0))) {
                firstSystem = node;
            }

            if (node.NAME.equalsIgnoreCase(ARGS.get(1))) {
                secondSystem = node;
            }
        }

        if (firstSystem == null) {
            event.reply(ARGS.get(0) + " isn't in the route.");
            return;
        }

        if (secondSystem == null) {
            event.reply(ARGS.get(1) + " isn't in the route.");
            return;
        }

        firstSystem.addDestination(secondSystem, 0);
        route.recalculate();

        event.reply(route.info());
    }

}
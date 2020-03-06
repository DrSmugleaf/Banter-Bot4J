package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.eve.Route;
import com.github.drsmugleaf.eve.SystemGraph;
import discord4j.core.object.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(
        tags = {Tags.DELETE_COMMAND_MESSAGE},
        description = "Find the fastest route between two Eve Online systems"
)
public class EveRoute extends Command {

    static final Map<User, Route> ROUTES = new HashMap<>();

    @Argument(position = 1, examples = "O-VWPB")
    private String from;

    @Argument(position = 2, examples = "Jita")
    private String to;

    @Override
    public void run() {
        User author = EVENT
                .getMessage()
                .getAuthor()
                .orElseThrow(() -> new IllegalStateException("Couldn't get the message's author. Message: " + EVENT.getMessage()));
        Route route = SystemGraph.getRoute(from, to);

        if (route == null) {
            reply("No route found from system " + from  + " to system " + to + ".").subscribe();
            return;
        }

        ROUTES.put(author, route);
        reply(route.info()).subscribe();
    }

}

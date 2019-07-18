package com.github.drsmugleaf.commands.help;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.registry.Entry;
import com.google.common.collect.ImmutableList;

/**
 * Created by DrSmugleaf on 18/07/2019
 */
@CommandInfo(
        description = "List of commands"
)
public class Help extends Command {

    @Override
    public void run() {
        ImmutableList<Entry> commands = BanterBot4J.getHandler().getRegistry().getEntries();
        StringBuilder response = new StringBuilder();
        for (Entry command : commands) {
            String name = command.getName();
            String description = command.getCommandInfo().description();

            response
                    .append("**")
                    .append(name)
                    .append("**: ")
                    .append(description)
                    .append("\n");
        }

        reply(response.toString()).subscribe();
    }

}

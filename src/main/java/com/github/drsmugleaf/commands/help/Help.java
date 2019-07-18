package com.github.drsmugleaf.commands.help;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.registry.Entry;
import com.google.common.collect.ImmutableList;

/**
 * Created by DrSmugleaf on 18/07/2019
 */
public class Help extends Command {

    @Override
    public void run() {
        ImmutableList<Entry> commands = BanterBot4J.getHandler().getRegistry().getEntries();
        StringBuilder response = new StringBuilder();
        for (Entry command : commands) {
            response
                    .append(command.getExamples())
                    .append("\n");
        }

        reply(response.toString()).subscribe();
    }

}

package com.github.drsmugleaf.commands.help;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.converter.ConverterRegistry;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import com.github.drsmugleaf.commands.api.registry.Entry;
import com.google.common.collect.ImmutableList;

/**
 * Created by DrSmugleaf on 18/07/2019
 */
@CommandInfo(
        description = "List of commands and usages for specific commands"
)
public class Help extends Command {

    @Argument(position = 1, examples = "play", maxWords = Integer.MAX_VALUE, optional = true)
    @Nullable
    private Entry command;

    @Override
    public void run() {
        if (command == null) {
            ImmutableList<Entry> commands = BanterBot4J.getHandler().getRegistry().getEntries();
            StringBuilder response = new StringBuilder("\n");
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
        } else {
            reply("\n" + command.getFormatsExamples()).subscribe();
        }
    }

    @Override
    public void registerConverters(ConverterRegistry converter) {
        converter.registerCommandTo(Entry.class, (s, e) -> {
            CommandSearchResult search = BanterBot4J.getHandler().getRegistry().findCommand(s);
            return search == null ? null : search.getEntry();
        });
    }

}

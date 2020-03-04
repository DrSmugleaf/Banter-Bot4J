package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(
        tags = {Tags.OWNER_ONLY},
        description = "Change the bot's name"
)
public class Name extends Command {

    @Argument(position = 1, examples = "Banter Bot4J", maxWords = Integer.MAX_VALUE, minimum = 2, maximum = 32)
    private String name;

    @Override
    public void run() {
        EVENT
                .getClient()
                .edit(self -> self.setUsername(ARGUMENTS.toString()))
                .flatMap(newName -> reply("Changed the bot's name to " + name))
                .subscribe();
    }

}

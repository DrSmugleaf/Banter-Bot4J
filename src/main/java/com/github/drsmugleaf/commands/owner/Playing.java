package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(
        tags = {Tags.OWNER_ONLY},
        description = "Change the bot's playing status"
)
public class Playing extends Command {

    @Argument(position = 1, examples = "!help", maxWords = Integer.MAX_VALUE, optional = true)
    @Nullable
    private String name;

    @Override
    public void run() {
        if(name == null) {
            EVENT
                    .getClient()
                    .updatePresence(Presence.online())
                    .then(reply("Reset the bot's playing status"))
                    .subscribe();
            return;
        }

        EVENT
                .getClient()
                .updatePresence(Presence.online(Activity.playing(name)))
                .then(reply("Changed the bot's playing status to " + name))
                .subscribe();
    }

}

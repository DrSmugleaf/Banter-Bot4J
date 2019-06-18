package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(tags = {Tags.OWNER_ONLY})
public class Playing extends Command {

    @Override
    public void run() {
        if(ARGUMENTS.isEmpty()) {
            EVENT
                    .getClient()
                    .updatePresence(Presence.online())
                    .then(reply("Reset the bot's playing status"))
                    .subscribe();
            return;
        }

        String game = String.join(" ", ARGUMENTS);
        EVENT
                .getClient()
                .updatePresence(Presence.online(Activity.playing(game)))
                .then(reply("Changed the bot's playing status to " + game))
                .subscribe();
    }

}

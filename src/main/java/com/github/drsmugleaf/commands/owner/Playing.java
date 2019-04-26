package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(tags = {Tags.OWNER_ONLY})
public class Playing extends Command {

    @Override
    public void run() {
        if(ARGUMENTS.isEmpty()) {
            EVENT.getClient().changePresence(StatusType.ONLINE, null, "");
            EVENT.reply("Reset the bot's playing status");
            return;
        }

        String game = String.join(" ", ARGUMENTS);
        EVENT.getClient().changePresence(StatusType.ONLINE, ActivityType.PLAYING, game);
        EVENT.reply("Changed the bot's playing status to " + game);
    }

}

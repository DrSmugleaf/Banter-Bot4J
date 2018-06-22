package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.*;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(tags = {Tags.OWNER_ONLY})
public class Playing extends Command {

    protected Playing(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        if(ARGS.isEmpty()) {
            event.getClient().changePresence(StatusType.ONLINE, null, "");
            event.reply("Reset the bot's playing status");
            return;
        }

        String game = String.join(" ", ARGS);
        event.getClient().changePresence(StatusType.ONLINE, ActivityType.PLAYING, game);
        event.reply("Changed the bot's playing status to " + game);
    }

}

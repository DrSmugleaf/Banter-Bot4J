package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.Tags;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(tags = {Tags.OWNER_ONLY})
public class Name extends Command {

    @Override
    protected void run(@Nonnull CommandReceivedEvent event) {
        String name = String.join(" ", event.ARGS);
        event.getClient().changeUsername(String.join(" ", event.ARGS));
        event.reply("Changed the bot's name to " + name);
    }

}

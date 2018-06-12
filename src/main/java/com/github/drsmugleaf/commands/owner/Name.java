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

    @Nonnull
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               BOT_PREFIX + "name username\n" +
               "**Examples:**\n" +
               BOT_PREFIX + "name Banter Bot4J";
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        String name = String.join(" ", event.ARGS);
        if (name.isEmpty()) {
            event.reply(wrongFormatResponse());
            return;
        }

        event.getClient().changeUsername(String.join(" ", event.ARGS));
        event.reply("Changed the bot's name to " + name);
    }

}

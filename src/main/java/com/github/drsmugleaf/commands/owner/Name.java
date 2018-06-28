package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.*;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(tags = {Tags.OWNER_ONLY})
public class Name extends Command {

    protected Name(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               BOT_PREFIX + "name username\n" +
               "**Examples:**\n" +
               BOT_PREFIX + "name Banter Bot4J";
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        String name = String.join(" ", ARGS);
        if (name.isEmpty()) {
            event.reply(wrongFormatResponse());
            return;
        }

        event.getClient().changeUsername(String.join(" ", ARGS));
        event.reply("Changed the bot's name to " + name);
    }

}

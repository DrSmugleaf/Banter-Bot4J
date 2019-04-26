package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(tags = {Tags.OWNER_ONLY})
public class Name extends Command {

    @Nonnull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "name username\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "name Banter Bot4J";
    }

    @Override
    public void run() {
        String name = String.join(" ", ARGUMENTS);
        if (name.isEmpty()) {
            EVENT.reply(invalidArgumentsResponse());
            return;
        }

        EVENT.getClient().changeUsername(String.join(" ", ARGUMENTS));
        EVENT.reply("Changed the bot's name to " + name);
    }

}

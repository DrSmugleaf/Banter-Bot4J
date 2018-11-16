package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.tags.Tags;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(tags = {Tags.OWNER_ONLY})
public class Name extends Command {

    protected Name(@NotNull CommandReceivedEvent event, @NotNull Arguments args) {
        super(event, args);
    }

    @NotNull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "name username\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "name Banter Bot4J";
    }

    @Override
    public void run() {
        String name = String.join(" ", ARGS);
        if (name.isEmpty()) {
            EVENT.reply(invalidArgumentsResponse());
            return;
        }

        EVENT.getClient().changeUsername(String.join(" ", ARGS));
        EVENT.reply("Changed the bot's name to " + name);
    }

}

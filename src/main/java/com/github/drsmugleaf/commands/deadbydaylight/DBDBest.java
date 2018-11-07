package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.deadbydaylight.dennisreep.PerksAPI;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class DBDBest extends Command {

    protected DBDBest(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest charactername\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest michael myers\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest trapper\n" +
               BanterBot4J.BOT_PREFIX + "dbdbest claudette";
    }

    @Override
    public void run() {
        if (ARGS.isEmpty()) {
            StringBuilder builder = new StringBuilder("Best Killer perks:\n");

            List<String> bestKillerPerks = PerksAPI.KILLER_PERKS.get().getBest(4).getNames();
            String bestKillerPerksNames = String.join(", ", bestKillerPerks);
            builder.append(bestKillerPerksNames).append("\nBest Survivor perks:\n");

            List<String> bestSurvivorPerks = PerksAPI.SURVIVOR_PERKS.get().getBest(4).getNames();
            String bestSurvivorPerksNames = String.join(", ", bestSurvivorPerks);
            builder.append(bestSurvivorPerksNames);

            EVENT.reply(builder.toString());
            return;
        }
    }

}

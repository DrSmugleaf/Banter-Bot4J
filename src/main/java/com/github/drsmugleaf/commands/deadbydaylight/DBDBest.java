package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.github.drsmugleaf.deadbydaylight.dennisreep.KillerPerk;
import com.github.drsmugleaf.deadbydaylight.dennisreep.PerksAPI;
import com.github.drsmugleaf.deadbydaylight.dennisreep.SurvivorPerk;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

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

            Set<Map.Entry<KillerPerks, KillerPerk>> killerPerks = PerksAPI.KILLER_PERKS.get().sortByValues().entrySet();
            List<String> bestKillerPerks = killerPerks
                    .stream()
                    .limit(4)
                    .map(entry -> entry.getValue().NAME)
                    .collect(Collectors.toCollection(ArrayList::new));

            builder.append(String.join(", ", bestKillerPerks)).append("\nBest Survivor perks:\n");

            Set<Map.Entry<SurvivorPerks, SurvivorPerk>> survivorPerks = PerksAPI.SURVIVOR_PERKS.get().sortByValues().entrySet();
            List<String> bestSurvivorPerks = survivorPerks
                    .stream()
                    .limit(4)
                    .map(entry -> entry.getValue().NAME)
                    .collect(Collectors.toCollection(ArrayList::new));

            builder.append(String.join(", ", bestSurvivorPerks));

            EVENT.reply(builder.toString());
            return;
        }
    }

}

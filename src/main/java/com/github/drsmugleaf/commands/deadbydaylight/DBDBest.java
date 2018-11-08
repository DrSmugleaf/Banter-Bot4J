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

    @Nonnull
    private static String getBestKillerPerksNames(int amount) {
        List<String> bestPerks = PerksAPI.KILLER_PERKS.get().getBest(amount).getNames();
        return String.join(", ", bestPerks);
    }

    @Nonnull
    private static String getBestSurvivorPerksNames(int amount) {
        List<String> bestPerks = PerksAPI.SURVIVOR_PERKS.get().getBest(amount).getNames();
        return String.join(", ", bestPerks);
    }

    @Nonnull
    private static String getBestPerksResponse(int amount) {
        if (amount == 1) {
            return "Best Killer perk:\n" +
                   getBestKillerPerksNames(1) + "\n" +
                   "Best Survivor perk:\n" +
                   getBestSurvivorPerksNames(1);
        }

        return "Best " + amount + " Killer perks:\n" +
               getBestKillerPerksNames(amount) + "\n" +
               "Best " + amount + " Survivor perks:\n" +
               getBestSurvivorPerksNames(amount);
    }

    @Override
    public void run() {
        if (ARGS.isEmpty()) {
            String response = getBestPerksResponse(4);
            EVENT.reply(response);
        } else if (ARGS.size() == 1 && ARGS.isInteger(0)) {
            int amount = ARGS.getInteger(0);
            String response = getBestPerksResponse(amount);
            EVENT.reply(response);
        }
    }

}

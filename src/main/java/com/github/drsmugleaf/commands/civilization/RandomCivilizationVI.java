package com.github.drsmugleaf.commands.civilization;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.civilization.vi.Leaders;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 31/08/2018
 */
@CommandInfo(aliases = "randomcivilization6")
public class RandomCivilizationVI extends Command {

    protected RandomCivilizationVI(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        if (ARGS.size() < 1) {
            EVENT.reply("Missing an amount of leaders to return.\n" +
                        "Format: " + BanterBot4J.BOT_PREFIX + "randomciviliation6 amount\n" +
                        "Example: " + BanterBot4J.BOT_PREFIX + "randomcivilization6 3");
            return;
        }

        if (!ARGS.isInteger(0)) {
            EVENT.reply("Invalid number of leaders requested: " + ARGS.get(0));
            return;
        }

        int amount = ARGS.getInteger(0);
        if (amount > Leaders.AMOUNT) {
            EVENT.reply("Too many leaders requested. Limit: " + Leaders.AMOUNT);
            return;
        }

        List<Leaders> leaders = Leaders.random(amount);
        List<String> leadersNamesList = leaders.stream().map(Leaders::getName).collect(Collectors.toList());
        String leadersNames = String.join(", ", leadersNamesList);

        EVENT.reply(leadersNames);
    }

}

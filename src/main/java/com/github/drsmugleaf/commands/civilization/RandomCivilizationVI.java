package com.github.drsmugleaf.commands.civilization;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.civilization.vi.Leaders;
import com.github.drsmugleaf.commands.api.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 31/08/2018
 */
@CommandInfo(aliases = {
        "random civilization vi", "random civilizationvi", "r civilization vi", "r civilizationvi",
        "randomcivvi", "random civ vi", "random civvi",
        "randomcivilization6", "rcivilization6",
        "random civilization 6", "random civilization6", "r civilization 6", "r civilization6",
        "randomciv6", "random civ 6", "random civ6", "r civ 6", "r civ6", "rciv6"
})
public class RandomCivilizationVI extends Command {

    @Argument(position = 1)
    private Integer leaders;

    protected RandomCivilizationVI(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    @Override
    public String invalidFormatResponse() {
        return "Format: " + BanterBot4J.BOT_PREFIX + "randomciviliation6 leaders\n" +
                "Example: " + BanterBot4J.BOT_PREFIX + "randomcivilization6 3";
    }

    @Override
    public void run() {
        if (leaders > Leaders.AMOUNT) {
            EVENT.reply("Too many leaders requested. Limit: " + Leaders.AMOUNT);
            return;
        }

        List<Leaders> leaders = Leaders.random(this.leaders);
        List<String> leadersNamesList = leaders.stream().map(Leaders::getName).collect(Collectors.toList());
        String leadersNames = String.join(", ", leadersNamesList);

        EVENT.reply(leadersNames);
    }

}

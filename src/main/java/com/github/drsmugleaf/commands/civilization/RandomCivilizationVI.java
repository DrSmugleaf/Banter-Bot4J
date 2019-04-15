package com.github.drsmugleaf.commands.civilization;

import com.github.drsmugleaf.civilization.vi.Leaders;
import com.github.drsmugleaf.commands.api.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 31/08/2018
 */
@CommandInfo(name = "rciv6", aliases = {
        "random civilization vi", "random civilizationvi", "r civilization vi", "r civilizationvi",
        "randomcivvi", "random civ vi", "random civvi",
        "randomcivilization6", "rcivilization6",
        "random civilization 6", "random civilization6", "r civilization 6", "r civilization6",
        "randomciv6", "random civ 6", "random civ6", "r civ 6", "r civ6"
})
public class RandomCivilizationVI extends Command {

    @Argument(position = 1, example = "3", maximum = 36) // maximum = Leaders.AMOUNT
    private Integer leaders;

    protected RandomCivilizationVI(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        List<Leaders> leaders = Leaders.random(this.leaders);
        List<String> leadersNamesList = leaders.stream().map(Leaders::getName).collect(Collectors.toList());
        String leadersNames = String.join(", ", leadersNamesList);

        EVENT.reply(leadersNames);
    }

}
